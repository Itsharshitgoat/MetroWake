package com.metrowake.app.services.tracking

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.metrowake.app.data.speech.SpeechEngine
import com.metrowake.app.domain.usecases.CalculateJourneyProgressUseCase
import com.metrowake.app.domain.usecases.MatchStationFromSpeechUseCase
import com.metrowake.app.services.notifications.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TrackingService : Service() {

    @Inject lateinit var notificationHelper: NotificationHelper
    @Inject lateinit var trackingEngine: TrackingEngine
    @Inject lateinit var speechEngine: SpeechEngine
    @Inject lateinit var matchStationUseCase: MatchStationFromSpeechUseCase
    @Inject lateinit var calculateProgressUseCase: CalculateJourneyProgressUseCase

    private val serviceScope = CoroutineScope(Dispatchers.IO + Job())
    private var listeningJob: Job? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_STOP_SERVICE) {
            stopSelf()
            return START_NOT_STICKY
        }

        startForeground(
            NotificationHelper.FOREGROUND_NOTIFICATION_ID,
            notificationHelper.getForegroundNotification("Listening for announcements...")
        )

        observeSpeechResults()
        startPeriodicListening()

        return START_STICKY
    }

    private fun observeSpeechResults() {
        serviceScope.launch {
            speechEngine.speechResults.collect { transcript ->
                Log.d("TrackingService", "Heard: $transcript")
                processTranscript(transcript)
            }
        }
    }

    private suspend fun processTranscript(transcript: String) {
        val matchResult = matchStationUseCase(transcript)
        if (matchResult != null) {
            Log.d("TrackingService", "Matched Station: ${matchResult.station.name} with score: ${matchResult.confidenceScore}")

            val state = trackingEngine.journeyState.value
            if (state is JourneyState.Active) {
                val progress = calculateProgressUseCase(matchResult.station.id, state.destinationStationId)
                if (progress != null) {
                    trackingEngine.updateProgress(
                        currentStationId = matchResult.station.id,
                        remainingStations = progress.remainingStations,
                        etaMinutes = progress.etaMinutes
                    )

                    val notifText = "${matchResult.station.name} -> ETA: ${progress.etaMinutes} min"
                    notificationHelper.updateForegroundNotification(notifText)

                    // Trigger alerts based on remaining stations
                    if (progress.remainingStations == 2) {
                        notificationHelper.showStationAlert("Approaching", "2 stations remaining.")
                    } else if (progress.remainingStations == 1) {
                        notificationHelper.showStationAlert("Get Ready", "1 station remaining.")
                    } else if (progress.remainingStations == 0) {
                        notificationHelper.showStationAlert("Destination Reached", "You have arrived.")
                        stopSelf()
                    }
                }
            }
        }
    }

    private fun startPeriodicListening() {
        listeningJob?.cancel()
        listeningJob = serviceScope.launch {
            while (true) {
                Log.d("TrackingService", "Waking microphone...")
                speechEngine.startListening()

                // Listen for 20 seconds
                delay(20_000L)

                Log.d("TrackingService", "Sleeping microphone...")
                speechEngine.stopListening()

                // Sleep for 4 minutes
                delay(240_000L)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listeningJob?.cancel()
        speechEngine.stopListening()
        trackingEngine.endJourney()
    }

    companion object {
        const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    }
}

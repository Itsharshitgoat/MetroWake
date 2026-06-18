package com.metrowake.app.services.tracking

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackingEngine @Inject constructor() {
    private val _journeyState = MutableStateFlow<JourneyState>(JourneyState.Idle)
    val journeyState: StateFlow<JourneyState> = _journeyState.asStateFlow()

    fun startJourney(startStationId: String, destStationId: String) {
        _journeyState.value = JourneyState.Active(
            currentStationId = startStationId,
            destinationStationId = destStationId,
            remainingStations = -1,
            etaMinutes = -1
        )
    }

    fun updateProgress(currentStationId: String, remainingStations: Int, etaMinutes: Int) {
        val currentState = _journeyState.value
        if (currentState is JourneyState.Active) {
            _journeyState.value = currentState.copy(
                currentStationId = currentStationId,
                remainingStations = remainingStations,
                etaMinutes = etaMinutes
            )
        }
    }

    fun endJourney() {
        _journeyState.value = JourneyState.Idle
    }
}

sealed class JourneyState {
    object Idle : JourneyState()
    data class Active(
        val currentStationId: String,
        val destinationStationId: String,
        val remainingStations: Int,
        val etaMinutes: Int
    ) : JourneyState()
}

package com.metrowake.app.presentation.journey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metrowake.app.data.repository.MetroRepository
import com.metrowake.app.services.tracking.JourneyState
import com.metrowake.app.services.tracking.TrackingEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JourneyViewModel @Inject constructor(
    private val trackingEngine: TrackingEngine,
    private val repository: MetroRepository
) : ViewModel() {

    val journeyState: StateFlow<JourneyState> = trackingEngine.journeyState

    private val _currentStationName = MutableStateFlow("Loading...")
    val currentStationName: StateFlow<String> = _currentStationName.asStateFlow()

    init {
        viewModelScope.launch {
            journeyState.collect { state ->
                if (state is JourneyState.Active) {
                    val station = repository.getStationById(state.currentStationId)
                    _currentStationName.value = station?.name ?: "Unknown Station"
                } else {
                    _currentStationName.value = "Journey Complete"
                }
            }
        }
    }

    fun startJourney(startId: String, destId: String) {
        trackingEngine.startJourney(startId, destId)
    }

    fun cancelJourney() {
        trackingEngine.endJourney()
    }
}

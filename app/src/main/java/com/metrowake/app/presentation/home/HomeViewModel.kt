package com.metrowake.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metrowake.app.data.local.StationEntity
import com.metrowake.app.data.repository.MetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MetroRepository
) : ViewModel() {

    private val _stations = MutableStateFlow<List<StationEntity>>(emptyList())
    val stations: StateFlow<List<StationEntity>> = _stations.asStateFlow()

    private val _selectedStart = MutableStateFlow<StationEntity?>(null)
    val selectedStart: StateFlow<StationEntity?> = _selectedStart.asStateFlow()

    private val _selectedDestination = MutableStateFlow<StationEntity?>(null)
    val selectedDestination: StateFlow<StationEntity?> = _selectedDestination.asStateFlow()

    init {
        loadStations()
    }

    private fun loadStations() {
        viewModelScope.launch {
            // Check if DB is empty, if so, initialize with sample data
            if (repository.getStationCount() == 0) {
                repository.initializeStations(com.metrowake.app.data.metro.MetroDataInitializer.initialStations)
            }
            _stations.value = repository.getAllStations()
        }
    }

    fun selectStart(station: StationEntity) {
        _selectedStart.value = station
    }

    fun selectDestination(station: StationEntity) {
        _selectedDestination.value = station
    }
}

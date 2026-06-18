package com.metrowake.app.data.repository

import com.metrowake.app.data.local.JourneyHistoryEntity
import com.metrowake.app.data.local.RouteEntity
import com.metrowake.app.data.local.StationEntity
import kotlinx.coroutines.flow.Flow

interface MetroRepository {
    // Stations
    suspend fun getAllStations(): List<StationEntity>
    suspend fun getStationById(id: String): StationEntity?
    suspend fun getStationsByName(name: String): List<StationEntity>
    suspend fun getStationsByLine(line: String): List<StationEntity>
    suspend fun initializeStations(stations: List<StationEntity>)
    suspend fun getStationCount(): Int

    // Routes
    fun getPopularRoutes(limit: Int = 10): Flow<List<RouteEntity>>
    fun getRecentRoutes(limit: Int = 10): Flow<List<RouteEntity>>
    fun getFavoriteRoutes(): Flow<List<RouteEntity>>
    suspend fun saveRoute(route: RouteEntity)
    suspend fun incrementRouteUsage(routeId: Int, timestamp: Long)

    // Journey History
    fun getJourneyHistory(): Flow<List<JourneyHistoryEntity>>
    suspend fun saveJourney(journey: JourneyHistoryEntity)
    fun getTotalJourneys(): Flow<Int>
    fun getTotalStationsCrossed(): Flow<Int>
    fun getTotalDurationMinutes(): Flow<Int>
}

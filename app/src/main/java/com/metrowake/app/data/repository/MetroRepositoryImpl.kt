package com.metrowake.app.data.repository

import com.metrowake.app.data.local.JourneyHistoryDao
import com.metrowake.app.data.local.JourneyHistoryEntity
import com.metrowake.app.data.local.RouteDao
import com.metrowake.app.data.local.RouteEntity
import com.metrowake.app.data.local.StationDao
import com.metrowake.app.data.local.StationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MetroRepositoryImpl @Inject constructor(
    private val stationDao: StationDao,
    private val routeDao: RouteDao,
    private val journeyHistoryDao: JourneyHistoryDao
) : MetroRepository {

    override suspend fun getAllStations(): List<StationEntity> {
        return stationDao.getAllStations()
    }

    override suspend fun getStationById(id: String): StationEntity? {
        return stationDao.getStationById(id)
    }

    override suspend fun getStationsByName(name: String): List<StationEntity> {
        return stationDao.getStationsByName(name)
    }

    override suspend fun getStationsByLine(line: String): List<StationEntity> {
        return stationDao.getStationsByLine(line)
    }

    override suspend fun initializeStations(stations: List<StationEntity>) {
        stationDao.insertStations(stations)
    }

    override suspend fun getStationCount(): Int {
        return stationDao.getStationCount()
    }

    override fun getPopularRoutes(limit: Int): Flow<List<RouteEntity>> {
        return routeDao.getPopularRoutes(limit)
    }

    override fun getRecentRoutes(limit: Int): Flow<List<RouteEntity>> {
        return routeDao.getRecentRoutes(limit)
    }

    override fun getFavoriteRoutes(): Flow<List<RouteEntity>> {
        return routeDao.getFavoriteRoutes()
    }

    override suspend fun saveRoute(route: RouteEntity) {
        routeDao.insertRoute(route)
    }

    override suspend fun incrementRouteUsage(routeId: Int, timestamp: Long) {
        routeDao.incrementRouteUsage(routeId, timestamp)
    }

    override fun getJourneyHistory(): Flow<List<JourneyHistoryEntity>> {
        return journeyHistoryDao.getJourneyHistory()
    }

    override suspend fun saveJourney(journey: JourneyHistoryEntity) {
        journeyHistoryDao.insertJourney(journey)
    }

    override fun getTotalJourneys(): Flow<Int> {
        return journeyHistoryDao.getTotalJourneys()
    }

    override fun getTotalStationsCrossed(): Flow<Int> {
        return journeyHistoryDao.getTotalStationsCrossed()
    }

    override fun getTotalDurationMinutes(): Flow<Int> {
        return journeyHistoryDao.getTotalDurationMinutes()
    }
}

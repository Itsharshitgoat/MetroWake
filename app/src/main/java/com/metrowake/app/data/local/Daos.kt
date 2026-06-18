package com.metrowake.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDao {
    @Query("SELECT * FROM stations")
    suspend fun getAllStations(): List<StationEntity>

    @Query("SELECT * FROM stations WHERE id = :id")
    suspend fun getStationById(id: String): StationEntity?

    @Query("SELECT * FROM stations WHERE name = :name")
    suspend fun getStationsByName(name: String): List<StationEntity>

    @Query("SELECT * FROM stations WHERE line = :line")
    suspend fun getStationsByLine(line: String): List<StationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStations(stations: List<StationEntity>)

    @Query("SELECT COUNT(*) FROM stations")
    suspend fun getStationCount(): Int
}

@Dao
interface RouteDao {
    @Query("SELECT * FROM routes ORDER BY usageCount DESC LIMIT :limit")
    fun getPopularRoutes(limit: Int): Flow<List<RouteEntity>>

    @Query("SELECT * FROM routes ORDER BY lastUsedTimestamp DESC LIMIT :limit")
    fun getRecentRoutes(limit: Int): Flow<List<RouteEntity>>

    @Query("SELECT * FROM routes WHERE isFavorite = 1")
    fun getFavoriteRoutes(): Flow<List<RouteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoute(route: RouteEntity)

    @Query("UPDATE routes SET usageCount = usageCount + 1, lastUsedTimestamp = :timestamp WHERE id = :routeId")
    suspend fun incrementRouteUsage(routeId: Int, timestamp: Long)
}

@Dao
interface JourneyHistoryDao {
    @Query("SELECT * FROM journey_history ORDER BY timestamp DESC")
    fun getJourneyHistory(): Flow<List<JourneyHistoryEntity>>

    @Insert
    suspend fun insertJourney(journey: JourneyHistoryEntity)

    @Query("SELECT COUNT(*) FROM journey_history")
    fun getTotalJourneys(): Flow<Int>

    @Query("SELECT SUM(stationsCrossed) FROM journey_history")
    fun getTotalStationsCrossed(): Flow<Int>

    @Query("SELECT SUM(durationMinutes) FROM journey_history")
    fun getTotalDurationMinutes(): Flow<Int>
}

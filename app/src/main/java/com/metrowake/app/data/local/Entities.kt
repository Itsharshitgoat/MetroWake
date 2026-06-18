package com.metrowake.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stations")
data class StationEntity(
    @PrimaryKey val id: String, // E.g., "rajiv_chowk_blue"
    val name: String,
    val line: String,
    val nextStationId: String?,
    val previousStationId: String?,
    val isInterchange: Boolean = false
)

@Entity(tableName = "routes")
data class RouteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val startStationId: String,
    val destinationStationId: String,
    val startStationName: String,
    val destinationStationName: String,
    val usageCount: Int = 0,
    val lastUsedTimestamp: Long = 0L,
    val isFavorite: Boolean = false
)

@Entity(tableName = "journey_history")
data class JourneyHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val startStationName: String,
    val destinationStationName: String,
    val timestamp: Long,
    val durationMinutes: Int,
    val stationsCrossed: Int
)

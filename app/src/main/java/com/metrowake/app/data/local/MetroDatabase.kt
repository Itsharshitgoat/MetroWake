package com.metrowake.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [StationEntity::class, RouteEntity::class, JourneyHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MetroDatabase : RoomDatabase() {
    abstract fun stationDao(): StationDao
    abstract fun routeDao(): RouteDao
    abstract fun journeyHistoryDao(): JourneyHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: MetroDatabase? = null

        fun getDatabase(context: Context): MetroDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MetroDatabase::class.java,
                    "metro_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

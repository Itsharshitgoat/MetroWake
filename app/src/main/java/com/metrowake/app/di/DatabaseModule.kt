package com.metrowake.app.di

import android.content.Context
import com.metrowake.app.data.local.JourneyHistoryDao
import com.metrowake.app.data.local.MetroDatabase
import com.metrowake.app.data.local.RouteDao
import com.metrowake.app.data.local.StationDao
import com.metrowake.app.data.repository.MetroRepository
import com.metrowake.app.data.repository.MetroRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMetroDatabase(@ApplicationContext context: Context): MetroDatabase {
        return MetroDatabase.getDatabase(context)
    }

    @Provides
    fun provideStationDao(database: MetroDatabase): StationDao {
        return database.stationDao()
    }

    @Provides
    fun provideRouteDao(database: MetroDatabase): RouteDao {
        return database.routeDao()
    }

    @Provides
    fun provideJourneyHistoryDao(database: MetroDatabase): JourneyHistoryDao {
        return database.journeyHistoryDao()
    }

    @Provides
    @Singleton
    fun provideMetroRepository(
        stationDao: StationDao,
        routeDao: RouteDao,
        journeyHistoryDao: JourneyHistoryDao
    ): MetroRepository {
        return MetroRepositoryImpl(stationDao, routeDao, journeyHistoryDao)
    }
}

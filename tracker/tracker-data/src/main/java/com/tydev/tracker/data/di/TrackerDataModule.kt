package com.tydev.tracker.data.di

import android.content.Context
import androidx.room.Room
import com.tydev.tracker.data.local.TrackerDatabase
import com.tydev.tracker.data.remote.OpenFoodApi
import com.tydev.tracker.data.repository.TrackerRepositoryImpl
import com.tydev.tracker.domain.repository.TrackerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrackerDataModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideTrackerDatabase(
        @ApplicationContext context: Context,
    ): TrackerDatabase {
        return Room.databaseBuilder(
            context,
            TrackerDatabase::class.java,
            "tracker_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTrackerRepository(
        api: OpenFoodApi,
        db: TrackerDatabase
    ): TrackerRepository {
        return TrackerRepositoryImpl(
            dao = db.dao,
            api = api
        )
    }
}

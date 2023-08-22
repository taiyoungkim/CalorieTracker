package com.tydev.core.di.repository

import com.tydev.core.datastore.UserPreferencesDataSource
import com.tydev.core.datastore.repository.UserDataRepositoryImpl
import com.tydev.core.domain.repository.UserDataRepository
import com.tydev.tracker.data.local.TrackerDatabase
import com.tydev.tracker.data.remote.OpenFoodApi
import com.tydev.tracker.data.repository.TrackerRepositoryImpl
import com.tydev.tracker.domain.repository.TrackerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        userPreferencesDataSource: UserPreferencesDataSource,
    ): UserDataRepository {
        return UserDataRepositoryImpl(userPreferencesDataSource)
    }

    @Provides
    @Singleton
    fun provideTrackerRepository(
        api: OpenFoodApi,
        db: TrackerDatabase,
    ): TrackerRepository {
        return TrackerRepositoryImpl(
            dao = db.dao,
            api = api,
        )
    }
}

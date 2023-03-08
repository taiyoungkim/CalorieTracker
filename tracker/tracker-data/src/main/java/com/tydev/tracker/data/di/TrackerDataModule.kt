package com.tydev.tracker.data.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tydev.tracker.data.local.TrackerDatabase
import com.tydev.tracker.data.remote.OpenFoodApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
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
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenFoodApi(
        client: OkHttpClient,
        networkJson: Json
    ): OpenFoodApi {
        return Retrofit.Builder()
            .baseUrl(OpenFoodApi.BASE_URL)
            .addConverterFactory(
                @OptIn(ExperimentalSerializationApi::class)
                networkJson.asConverterFactory("application/json".toMediaType())
            )
            .client(client)
            .build()
            .create()
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
}

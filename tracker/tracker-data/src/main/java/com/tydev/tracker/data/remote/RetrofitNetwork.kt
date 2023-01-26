package com.tydev.tracker.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tydev.tracker.data.dto.SearchDto
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Inject

class RetrofitNetwork @Inject constructor(
    networkJson: Json
) : OpenFoodApi {

    private val networkApi = Retrofit.Builder()
        .baseUrl(OpenFoodApi.BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    // TODO: Decide logging logic
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                )
                .build()
        )
        .addConverterFactory(
            @OptIn(ExperimentalSerializationApi::class)
            networkJson.asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(OpenFoodApi::class.java)

    override suspend fun searchFood(query: String, page: Int, pageSize: Int): SearchDto =
        networkApi.searchFood(query, page, pageSize)
}

package com.example.githubusers.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpClient {

    private lateinit var retrofit: Retrofit

    private const val BASE_URL = "https://api.github.com/"

    val retrofitInstance: Retrofit
        get() {
            if (!this::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}
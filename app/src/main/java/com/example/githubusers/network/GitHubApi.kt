package com.example.githubusers.network

import com.example.githubusers.data.User
import com.example.githubusers.data.UserInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GitHubApi {
    @GET("users")
    suspend fun getUsers(@Query("per_page") paramValue: String = "14"): List<User?>?

    @GET("users/{user}")
    suspend fun getUserInfoByName(@Path("user") user: String): UserInfo?

    @GET("users/{user}/followers")
    suspend fun getUserFollowers(@Path("user") user: String, @Query("per_page") paramValue: String = "14"): List<User?>?
}
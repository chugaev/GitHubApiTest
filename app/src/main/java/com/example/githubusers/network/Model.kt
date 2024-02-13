package com.example.githubusers.network

import com.example.githubusers.data.User
import com.example.githubusers.data.UserInfo

class Model {
    private val gitHubApi = HttpClient.retrofitInstance.create(GitHubApi::class.java)
    suspend fun getUsers(): List<User> {
        return gitHubApi.getUsers()
    }

    suspend fun getFollowers(user: String): List<User> {
        return gitHubApi.getUserFollowers(user)
    }

    suspend fun getDetailedUserInformation(login: String): UserInfo {
        return gitHubApi.getUserInfoByName(login)
    }
}
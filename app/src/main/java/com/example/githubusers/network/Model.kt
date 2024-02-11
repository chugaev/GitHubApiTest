package com.example.githubusers.network

import com.example.githubusers.data.User
import com.example.githubusers.data.UserInfo
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class Model {
    suspend fun getUsers(): List<UserInfo?>{
        val gitHubApi = HttpClient.retrofitInstance.create(GitHubApi::class.java)
        val users = gitHubApi.getUsers()
        return getDetailedUserInformation(gitHubApi, users)
    }

    suspend fun getFollowers(user: String): List<UserInfo?>{
        val gitHubApi = HttpClient.retrofitInstance.create(GitHubApi::class.java)
        val users = gitHubApi.getUserFollowers(user)
        return getDetailedUserInformation(gitHubApi, users)
    }

    private suspend fun getDetailedUserInformation(gitHubApi: GitHubApi, users: List<User?>?): List<UserInfo?> {
        val usersInfo: List<UserInfo?> = coroutineScope {
            users!!.map { user -> async { gitHubApi.getUserInfoByName(user?.login!!) } }.awaitAll()
        }
        return usersInfo
    }
}
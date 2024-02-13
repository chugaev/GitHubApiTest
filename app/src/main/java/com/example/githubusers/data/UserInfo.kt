package com.example.githubusers.data

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("login")
    val login: String?,

    @SerializedName("followers")
    val followersNumber: Int,

    @SerializedName("public_repos")
    val reposNumber: Int
)
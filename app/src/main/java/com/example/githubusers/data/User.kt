package com.example.githubusers.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int,

    @SerializedName("login")
    val login: String?,

    @SerializedName("avatar_url")
    val avatar: String?
)
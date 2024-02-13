package com.example.githubusers.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int,

    @SerializedName("login")
    val login: String?,

    @SerializedName("avatar_url")
    val avatar: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("company")
    val company: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("blog")
    val blog: String?,

    @SerializedName("location")
    val location: String?,

    @SerializedName("bio")
    val bio: String?
)
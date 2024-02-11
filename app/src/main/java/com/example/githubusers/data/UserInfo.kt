package com.example.githubusers.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfo(
    @SerializedName("id")
    val id: Int,

    @SerializedName("login")
    val login: String?,

    @SerializedName("avatar_url")
    val avatar: String?,

    @SerializedName("followers")
    val followersNumber: Int,

    @SerializedName("public_repos")
    val reposNumber: Int,

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
) : Parcelable
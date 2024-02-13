package com.example.githubusers.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserFullInfo(
    var loading: Boolean,
    val id: Int,
    val login: String,
    val avatar: String,
    var followersNumber: Int,
    var reposNumber: Int,
    val name: String,
    val company: String,
    val email: String,
    val blog: String,
    val location: String,
    val bio: String
) : Parcelable {
    constructor(user: User, loading: Boolean)
            : this(loading,
        user.id,
        user.login ?: "",
        user.avatar ?: "",
        -1, -1,
        user.name ?: "",
        user.company ?: "",
        user.email ?: "",
        user.blog ?: "",
        user.location ?: "",
        user.bio ?: "")
}
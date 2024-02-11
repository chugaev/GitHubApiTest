package com.example.githubusers.ui

import com.example.githubusers.data.UserInfo

interface RecyclerViewClickListener {
    fun onItemClick(userInfo: UserInfo)
}
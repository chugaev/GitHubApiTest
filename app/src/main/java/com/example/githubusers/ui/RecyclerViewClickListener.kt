package com.example.githubusers.ui

import com.example.githubusers.data.UserFullInfo
interface RecyclerViewClickListener {
    fun onItemClick(userInfo: UserFullInfo)
}
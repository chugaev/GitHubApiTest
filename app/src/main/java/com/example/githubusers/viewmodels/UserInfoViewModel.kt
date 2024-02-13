package com.example.githubusers.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubusers.Action
import com.example.githubusers.NotificationAction
import com.example.githubusers.data.UserFullInfo
import com.example.githubusers.network.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserInfoViewModel : ViewModelWithException() {
    private val state = MutableLiveData<NotificationAction>()
    private val userList: ArrayList<UserFullInfo> = arrayListOf()
    private val model = Model()

    fun getStateLiveData(login: String): MutableLiveData<NotificationAction> {
        loadUsers(login)
        return state
    }

    fun getUserList(): ArrayList<UserFullInfo> {
        return userList
    }

    private fun loadUsers(login: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val users = model.getFollowers(login)
                userList.clear()
                withContext(Dispatchers.Main) {
                    userList.addAll(users.map { user -> UserFullInfo(user, true) })
                    state.value = NotificationAction(Action.INIT, -1)
                }
                for (i in 0 until userList.size) {
                    launch {
                        try {
                            val user = userList[i]
                            val info = model.getDetailedUserInformation(user.login)
                            withContext(Dispatchers.Main) {
                                user.loading = false
                                user.reposNumber = info.reposNumber
                                user.followersNumber = info.followersNumber
                                state.value = NotificationAction(Action.POSITION_CHANGED, i)
                            }
                        } catch (e : Exception) {
                            withContext(Dispatchers.Main) {
                                sendException(e)
                            }
                        }
                    }
                }
            } catch (e : Exception) {
                withContext(Dispatchers.Main) {
                    sendException(e)
                }
            }
        }
    }
}
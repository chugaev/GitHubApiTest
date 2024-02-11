package com.example.githubusers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubusers.data.UserInfo
import com.example.githubusers.network.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserInfoViewModel : ViewModelWithException() {
    private var userListLiveData = MutableLiveData<List<UserInfo?>>()
    private val model = Model()

    fun getUserListLiveData(user: String): LiveData<List<UserInfo?>> {
        loadUsers(user)
        return userListLiveData
    }

    private fun loadUsers(user: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val users = model.getFollowers(user)
                launch(Dispatchers.Main) {
                    userListLiveData.value = users
                }
            } catch (e : Exception) {
                launch(Dispatchers.Main) {
                    sendException(e)
                }
            }
        }
    }
}
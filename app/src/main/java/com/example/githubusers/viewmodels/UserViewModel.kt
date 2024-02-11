package com.example.githubusers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubusers.data.UserInfo
import com.example.githubusers.network.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel : ViewModelWithException() {
    private var userListLiveData: MutableLiveData<List<UserInfo?>> = MutableLiveData()
    private val model = Model()

    fun getUserListLiveData(): LiveData<List<UserInfo?>> {
        loadUsers()
        return userListLiveData
    }

    private fun loadUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val users = model.getUsers()
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
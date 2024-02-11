package com.example.githubusers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class ViewModelWithException : ViewModel() {
    private val exceptionLiveData = MutableLiveData<Exception>()

    fun getExceptionLiveData(): LiveData<Exception> {
        return exceptionLiveData
    }

    fun sendException(exception: Exception) {
        exceptionLiveData.value = exception
    }
}
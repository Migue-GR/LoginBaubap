package com.loginbaubap.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object AppSession {
    private val mGlobalError = MutableLiveData<LoginBaubapException>()
    val globalError = mGlobalError as LiveData<LoginBaubapException>
    fun showGlobalError(e: LoginBaubapException) = mGlobalError.postValue(e)

    private val mGlobalProgressBar = MutableLiveData<Boolean>()
    val globalProgressBar = mGlobalProgressBar as LiveData<Boolean>
    fun showGlobalProgressBar(showLoading: Boolean) = mGlobalProgressBar.postValue(showLoading)
}
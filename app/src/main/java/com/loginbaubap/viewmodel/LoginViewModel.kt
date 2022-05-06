package com.loginbaubap.viewmodel

import androidx.lifecycle.ViewModel
import com.loginbaubap.domain.LoginUseCase
import com.loginbaubap.utils.ext.resultLiveData

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    fun login(email: String, password: String) = resultLiveData {
        loginUseCase(email, password)
    }
}
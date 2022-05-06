package com.loginbaubap.domain

import com.loginbaubap.repository.UserRepository
import com.loginbaubap.utils.ext.useCaseExecution
import kotlinx.coroutines.Dispatchers

class LoginUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(
        email: String,
        password: String
    ) = useCaseExecution(Dispatchers.IO) {
        repository.login(email, password)
    }
}
package com.loginbaubap.datasource.remote

import com.loginbaubap.model.enums.ErrorCode
import com.loginbaubap.model.remote.UserDto
import com.loginbaubap.utils.LoginBaubapException
import java.util.*
import kotlin.random.Random

class RemoteUserDataSource {
    suspend fun login(email: String, password: String): UserDto {
        simulateHttpError()
        return UserDto(UUID.randomUUID().toString(), email)
    }

    private fun simulateHttpError() {
        if (Random.nextBoolean()) {
            throw LoginBaubapException(ErrorCode.ERROR_INVALID_CREDENTIALS)
        }
    }
}
package com.loginbaubap.repository

import com.loginbaubap.datasource.local.LocalUserDataSource
import com.loginbaubap.datasource.remote.RemoteUserDataSource
import com.loginbaubap.model.local.User
import com.loginbaubap.model.remote.toLocalUser

class UserRepository(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val localUserDataSource: LocalUserDataSource
) {
    suspend fun login(email: String, password: String): User {
        val remoteUser = remoteUserDataSource.login(email, password)
        localUserDataSource.saveUser()
        return remoteUser.toLocalUser()
    }
}
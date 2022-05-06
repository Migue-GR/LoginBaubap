package com.loginbaubap.model.remote

import com.loginbaubap.model.local.User

data class UserDto(
    val id: String?,
    val email: String?
)

fun UserDto.toLocalUser() = User(id ?: "", email ?: "")
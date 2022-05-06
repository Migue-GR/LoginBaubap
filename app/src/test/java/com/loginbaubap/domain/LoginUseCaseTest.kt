package com.loginbaubap.domain

import com.loginbaubap.model.local.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {
    private lateinit var testSubject: LoginUseCase

    @Before
    fun setup() {
        testSubject = mockk()
    }

    @Test
    fun `perform successful login`() = runBlocking {
        val mockResponse = User(RIGHT_ID, RIGHT_EMAIL)
        coEvery { testSubject(RIGHT_EMAIL, RIGHT_PASSWORD) } returns mockResponse
        val result = testSubject(RIGHT_EMAIL, RIGHT_PASSWORD)
        assert(result == mockResponse)
    }

    companion object {
        private const val RIGHT_ID = "1"
        private const val RIGHT_EMAIL = "miguenew01@gmail.com"
        private const val RIGHT_PASSWORD = "123"
    }
}
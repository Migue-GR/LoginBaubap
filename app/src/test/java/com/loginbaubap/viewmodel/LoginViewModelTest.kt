package com.loginbaubap.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.loginbaubap.model.enums.ErrorCode.ERROR_INVALID_CREDENTIALS
import com.loginbaubap.model.local.User
import com.loginbaubap.utils.LoginBaubapException
import com.loginbaubap.utils.UseCaseResult
import com.loginbaubap.utils.ext.resultLiveData
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()
    private lateinit var testSubject: LoginViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        testSubject = mockk()
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login with valid credentials`() = runBlocking {
        val mockUserResponse = User(RIGHT_ID, RIGHT_EMAIL)
        val mockResponse = UseCaseResult.Success(mockUserResponse)
        every {
            testSubject.login(RIGHT_EMAIL, RIGHT_PASSWORD)
        } answers {
            resultLiveData {
                mockUserResponse
            }
        }

        testSubject.login(RIGHT_EMAIL, RIGHT_PASSWORD).observeForever { result ->
            if (result is UseCaseResult.Success) {
                assert(result == mockResponse)
            }
        }
    }

    @Test
    fun `login with invalid credentials`() = runBlocking {
        val mockBaubapException = LoginBaubapException(ERROR_INVALID_CREDENTIALS)
        val mockResponse = UseCaseResult.Error(mockBaubapException)
        every {
            testSubject.login(WRONG_EMAIL, WRONG_PASSWORD)
        } answers {
            resultLiveData {
                throw mockBaubapException
            }
        }

        testSubject.login(WRONG_EMAIL, WRONG_PASSWORD).observeForever { result ->
            if (result is UseCaseResult.Error) {
                assert(result == mockResponse)
            }
        }
    }

    companion object {
        private const val RIGHT_ID = "1"
        private const val RIGHT_EMAIL = "miguenew01@gmail.com"
        private const val RIGHT_PASSWORD = "123"
        private const val WRONG_EMAIL = "wrong@email.com"
        private const val WRONG_PASSWORD = "wrong_password"
    }
}
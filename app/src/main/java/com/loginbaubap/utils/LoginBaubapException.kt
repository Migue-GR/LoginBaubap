package com.loginbaubap.utils

import com.loginbaubap.model.enums.ErrorCode

class LoginBaubapException(val errorCode: ErrorCode = ErrorCode.SOMETHING_WENT_WRONG) :
    Exception() {
    override fun getLocalizedMessage(): String = errorMessageFactory(errorCode)

    /**
     * @param errorCode Code describing the exception.
     * @return an error message by a given [ErrorCode].
     */
    private fun errorMessageFactory(errorCode: ErrorCode) = when (errorCode) {
        ErrorCode.NOTHING_HAPPENED -> ""
        ErrorCode.SOMETHING_WENT_WRONG -> "Something went wrong"
        ErrorCode.ERROR_INVALID_CREDENTIALS -> "Invalid login credentials"
    }
}
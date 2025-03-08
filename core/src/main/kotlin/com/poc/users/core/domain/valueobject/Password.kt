package com.poc.users.core.domain.valueobject

import com.poc.users.core.domain.exception.UserCreationException

data class Password(val value: String, val encryptedValue: String) {
    init {
        validatePassword(value)
    }

    private fun validatePassword(password: String) {
        if (password.length < PASSWORD_MIN_LENGTH) throw UserCreationException(ERROR_MIN_LENGTH)
        if (!password.any { it.isUpperCase() }) throw UserCreationException(ERROR_UPPERCASE)
        if (!password.any { it.isLowerCase() }) throw UserCreationException(ERROR_LOWERCASE)
        if (!password.any { it.isDigit() }) throw UserCreationException(ERROR_DIGIT)
        if (!password.any { !it.isLetterOrDigit() }) throw UserCreationException(ERROR_SPECIAL_CHARACTER)
    }

    companion object {
        const val PASSWORD_MIN_LENGTH = 8
        const val ERROR_MIN_LENGTH = "Password must be at least 8 characters long"
        const val ERROR_UPPERCASE = "Password must contain at least one uppercase letter"
        const val ERROR_LOWERCASE = "Password must contain at least one lowercase letter"
        const val ERROR_DIGIT = "Password must contain at least one digit"
        const val ERROR_SPECIAL_CHARACTER = "Password must contain at least one special character"

        val DEFAULT = Password(value = "<PaSSW0RD>", encryptedValue = "")
    }

}

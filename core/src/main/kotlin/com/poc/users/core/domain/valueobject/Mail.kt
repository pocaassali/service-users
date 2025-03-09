package com.poc.users.core.domain.valueobject

import com.poc.users.core.domain.exception.UserCreationException
import java.util.regex.Pattern

data class Mail(val value: String){
    init {
        validateMail()
    }

    fun validateMail() {
        val emailRegex = "^[A-Za-z0-9_-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,}$"
        val pattern = Pattern.compile(emailRegex)
        if(!pattern.matcher(value).matches()) throw UserCreationException(WRONG_FORMAT_EMAIL)
    }

    companion object {
        const val WRONG_FORMAT_EMAIL = "Mail has wrong format"
    }
}

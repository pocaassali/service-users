package com.poc.users.core.domain.service

import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.domain.exception.UserCreationException
import com.poc.users.core.domain.model.User
import java.util.Optional

class UserService(private val users: Users) {

    fun createUser(user: User): Optional<User> {
        doesNotExist(user)
        return users.save(user)
    }

    private fun doesNotExist(user: User) {
        if (users.findById(user.identifier).isPresent) {
            throw UserCreationException(NOT_UNIQUE_IDENTIFIER_ERROR)
        }
        if (users.findByMail(user.mail).isPresent) {
            throw UserCreationException(NOT_UNIQUE_MAIL_ERROR)
        }
    }

    companion object {
        private const val NOT_UNIQUE_IDENTIFIER_ERROR = "Oops something went wrong with ID !"
        private const val NOT_UNIQUE_MAIL_ERROR = "Oops something went wrong with MAIL !"
    }

}
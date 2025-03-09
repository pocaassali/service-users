package com.poc.users.core.domain.service

import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.ddd.DomainService
import com.poc.users.core.domain.exception.UserCreationException
import com.poc.users.core.domain.exception.UserNotFoundException
import com.poc.users.core.domain.model.User
import com.poc.users.core.domain.model.UserUpdateHelper
import java.util.*

@DomainService
class UserService(private val users: Users) {

    fun createUser(user: User): Optional<User> {
        doesNotExist(user)

        return users.save(user)
    }

    fun updateUser(user: UserUpdateHelper, identifier: UUID): Optional<User> {
        val optionalUser = users.findById(identifier)
        if (optionalUser.isEmpty) throw UserNotFoundException(USER_NOT_FOUND_ERROR)
        val updatedUser = optionalUser.get().updateWith(user)
        return users.update(updatedUser)
    }

    fun deleteUser(identifier: UUID) {
        if (users.delete(identifier)) return
        else throw UserNotFoundException(USER_NOT_FOUND_ERROR)
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
        private const val USER_NOT_FOUND_ERROR = "User not found !"
    }

}
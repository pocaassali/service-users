package com.poc.users.core.domain.service

import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.domain.model.User
import java.util.Optional

class UserService(private val users: Users) {

    fun createUser(user: User) : Optional<User> {
        return users.save(user)
    }
}
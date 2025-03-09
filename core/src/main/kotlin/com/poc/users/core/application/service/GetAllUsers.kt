package com.poc.users.core.application.service

import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.ddd.Usecase
import com.poc.users.core.domain.model.User

@Usecase
class GetAllUsers(
    private val users: Users
){
    fun execute() : List<User> {
        return users.findAll()
    }
}

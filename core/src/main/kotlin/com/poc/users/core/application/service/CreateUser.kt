package com.poc.users.core.application.service

import com.poc.users.core.application.dto.command.CreateUserCommand
import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.domain.model.User

class CreateUser(
    private val users: Users
): AbstractCommandHandler<CreateUserCommand, User?>() {
    override fun execute(command: CreateUserCommand): User? {
        return users.save(command.toUser())
    }
}
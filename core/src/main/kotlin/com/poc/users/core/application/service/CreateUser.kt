package com.poc.users.core.application.service

import com.poc.users.core.application.dto.command.CreateUserCommand
import com.poc.users.core.domain.model.User
import com.poc.users.core.domain.service.UserService
import java.util.Optional

class CreateUser(
    private val service: UserService,
): AbstractCommandHandler<CreateUserCommand, Optional<User>>() {

    override fun execute(command: CreateUserCommand): Optional<User> {
        return service.createUser(command.toUser())
    }
}
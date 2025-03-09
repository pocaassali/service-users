package com.poc.users.core.application.service.usecase

import com.poc.users.core.application.dto.command.CreateUserCommand
import com.poc.users.core.ddd.Usecase
import com.poc.users.core.domain.model.User
import com.poc.users.core.domain.service.UserService
import java.util.Optional

@Usecase
class CreateUser(
    private val service: UserService,
): AbstractCommandHandler<CreateUserCommand, Optional<User>>() {

    override fun execute(command: CreateUserCommand): Optional<User> {
        return service.createUser(command.toUser())
    }
}
package com.poc.users.core.application.service

import com.poc.users.core.application.dto.command.UpdateUserCommand
import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.domain.model.User
import com.poc.users.core.domain.service.UserService
import java.util.Optional


class UpdateUser(
    private val service : UserService,
): AbstractCommandHandler<UpdateUserCommand, Optional<User>>() {
    override fun execute(command: UpdateUserCommand): Optional<User> {
        return service.updateUser(command.toUser())
    }
}

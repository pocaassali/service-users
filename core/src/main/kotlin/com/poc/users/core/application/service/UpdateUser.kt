package com.poc.users.core.application.service

import com.poc.users.core.application.dto.command.UpdateUserCommand
import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.domain.model.User


class UpdateUser(
    private val users: Users
): AbstractCommandHandler<UpdateUserCommand, User?>() {
    override fun execute(command: UpdateUserCommand): User? {
        return users.update(command.toUser())
    }
}

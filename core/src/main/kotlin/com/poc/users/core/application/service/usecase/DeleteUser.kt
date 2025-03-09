package com.poc.users.core.application.service.usecase

import com.poc.users.core.application.dto.command.DeleteUserCommand
import com.poc.users.core.ddd.Usecase
import com.poc.users.core.domain.service.UserService
import java.util.*

@Usecase
class DeleteUser(private val service: UserService) : AbstractCommandHandler<DeleteUserCommand, Unit>() {
    override fun execute(command: DeleteUserCommand) {
        service.deleteUser(UUID.fromString(command.id))
    }

}

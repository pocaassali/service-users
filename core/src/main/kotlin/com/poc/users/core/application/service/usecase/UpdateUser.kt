package com.poc.users.core.application.service.usecase

import com.poc.users.core.application.dto.command.UpdateUserCommand
import com.poc.users.core.ddd.Usecase
import com.poc.users.core.domain.model.User
import com.poc.users.core.domain.service.UserService
import java.util.Optional

@Usecase
class UpdateUser(
    private val service : UserService,
): AbstractCommandHandler<UpdateUserCommand, Optional<User>>() {
    override fun execute(command: UpdateUserCommand): Optional<User> {
        return service.updateUser(identifier= command.identifier, user = command.toUser())
    }
}

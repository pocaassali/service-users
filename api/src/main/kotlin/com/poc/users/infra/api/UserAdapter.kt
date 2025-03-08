package com.poc.users.infra.api

import com.poc.users.core.application.dto.command.DeleteUserCommand
import com.poc.users.core.application.dto.query.GetUserByIdQuery
import com.poc.users.core.application.ports.input.UserApplicationService
import com.poc.users.core.domain.model.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserAdapter(
    private val applicationService: UserApplicationService,
    private val passwordEncoder: PasswordEncoder,
) {
    fun getAllUsers(): List<UserView> {
        return applicationService.getAllUsers().map { UserView.from(it) }
    }

    fun getUserById(id: String): UserView? {
        return applicationService.getUserById(GetUserByIdQuery(UUID.fromString(id))).getOrNull()
    }

    fun create(request: UserCreationRequest): UserView? {
        val command = request
            .toCommand(identifier = UUID.randomUUID(), encryptedPassword = passwordEncoder.encode(request.password))

        return applicationService
            .createUser(command)
            .getOrNull()
    }

    fun update(id: String, request: UserEditionRequest): UserView? {
        return applicationService.updateUser(
            request.toCommand(id, passwordEncoder.encode(request.password))
        )?.let { UserView.from(it) }
    }

    fun delete(id: String) {
        applicationService.deleteUser(DeleteUserCommand(id))
    }
}

fun Optional<User>.getOrNull(): UserView? = this.map { UserView.from(it) }.orElse(null)
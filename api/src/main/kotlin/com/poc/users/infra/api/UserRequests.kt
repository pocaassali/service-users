package com.poc.users.infra.api

import com.poc.users.core.application.dto.command.CreateUserCommand
import com.poc.users.core.application.dto.command.UpdateUserCommand
import java.util.*

data class UserCreationRequest(
    val mail : String,
    val password : String,
    val role : String = "USER",
) {
    fun toCommand(identifier: UUID, encryptedPassword : String) : CreateUserCommand {
        return CreateUserCommand(
            identifier = identifier,
            mail = mail,
            password = password,
            encryptedPassword = encryptedPassword,
            role = role,
        )
    }
}

data class UserEditionRequest(
    val mail : String,
    val password : String,
    val role : String,
) {
    fun toCommand(id: String, encryptedPassword : String) : UpdateUserCommand {
        return UpdateUserCommand(
            identifier = id,
            mail = mail,
            password = password,
            hashedPassword = encryptedPassword,
            role = role
        )
    }
}
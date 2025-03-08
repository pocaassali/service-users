package com.poc.users.infra.api

import com.poc.users.core.application.dto.command.CreateUserCommand
import com.poc.users.core.application.dto.command.UpdateUserCommand
import com.poc.users.core.application.dto.query.GetUserByCredentialsQuery
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
    fun toCommand(identifier: UUID, encryptedPassword : String) : UpdateUserCommand {
        return UpdateUserCommand(
            identifier = identifier,
            mail = mail,
            password = password,
            hashedPassword = encryptedPassword,
            role = role
        )
    }
}

data class UserLoginRequest(
    val mail : String,
){
    fun toQuery() : GetUserByCredentialsQuery = GetUserByCredentialsQuery(
        mail = mail,
    )
}
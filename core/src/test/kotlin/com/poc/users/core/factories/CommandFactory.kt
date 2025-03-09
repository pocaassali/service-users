package com.poc.users.core.factories

import com.poc.users.core.application.dto.command.CreateUserCommand
import java.util.*

fun aCreateUserCommand(
    identifier: String = "a2cbd57a-4103-4553-9986-b237e14bcb72",
    mail: String = "user@mail.com",
    password: String = "P@ssw0rd",
    role: String = "USER"
) = CreateUserCommand(
    identifier = UUID.fromString(identifier),
    mail = mail,
    password = password,
    encryptedPassword = "hashed_$password",
    role = role
)
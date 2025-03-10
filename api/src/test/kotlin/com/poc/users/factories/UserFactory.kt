package com.poc.users.factories

import com.poc.users.core.domain.model.User
import com.poc.users.core.domain.model.UserUpdateHelper
import com.poc.users.core.domain.valueobject.Mail
import com.poc.users.core.domain.valueobject.Password
import com.poc.users.core.domain.valueobject.UserRole
import com.poc.users.infra.api.UserView
import java.util.*

fun anUser(
    identifier: String = "a2cbd57a-4103-4553-9986-b237e14bcb72",
    mail: String = "user@mail.com",
    password: String = "P@ssw0rd",
) = User(
    identifier = UUID.fromString(identifier),
    mail = Mail(mail),
    password = Password(value = password, encryptedValue = "hashed_$password"),
    role = UserRole.USER
)

fun anAdmin(
    identifier: String = "a2cbd57a-4103-4553-9986-b237e14bcb70",
    mail: String = "admin@mail.com",
    password: String = "P@ssw0rd",
) = User(
    identifier = UUID.fromString(identifier),
    mail = Mail(mail),
    password = Password(value = password, encryptedValue = "hashed_$password"),
    role = UserRole.ADMIN
)

fun anUserView(
    identifier: String = "a2cbd57a-4103-4553-9986-b237e14bcb72",
    mail: String = "user@mail.com",
    role: String = "USER"
) = UserView(
    identifier = identifier,
    mail = mail,
    role = role
)
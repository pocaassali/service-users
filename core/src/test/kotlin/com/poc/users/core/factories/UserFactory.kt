package com.poc.users.core.factories

import com.poc.users.core.domain.model.User
import com.poc.users.core.domain.model.UserUpdateHelper
import com.poc.users.core.domain.valueobject.Mail
import com.poc.users.core.domain.valueobject.Password
import com.poc.users.core.domain.valueobject.UserRole
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

fun anUserUpdateHelper(
    mail: String? = null,
    password: String? = null,
    role: String? = null,
) = UserUpdateHelper(
    mail = Optional.ofNullable(mail?.let { Mail(it) }),
    password = Optional.ofNullable(password?.let { Password.DEFAULT.copy(encryptedValue = it) }),
    role = Optional.ofNullable(role?.let { UserRole.valueOf(it) }),
)
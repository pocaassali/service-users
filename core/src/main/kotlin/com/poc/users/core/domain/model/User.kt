package com.poc.users.core.domain.model

import com.poc.users.core.domain.valueobject.Mail
import com.poc.users.core.domain.valueobject.Password
import com.poc.users.core.domain.valueobject.UserRole
import java.util.*

data class User(
    val identifier: UUID, //TODO : update with Identifier class
    val mail: Mail,
    val password: Password,
    val role: UserRole,
) {

    fun updateWith(updatedUser: UserUpdateHelper): User {
        return User(
            identifier = identifier,
            mail = updatedUser.mail.orElse(mail),
            password = updatedUser.password.orElse(password),
            role = updatedUser.role.orElse(role)
        )
    }
}

data class UserUpdateHelper(
    val mail: Optional<Mail>,
    val password: Optional<Password>,
    val role: Optional<UserRole>,
)

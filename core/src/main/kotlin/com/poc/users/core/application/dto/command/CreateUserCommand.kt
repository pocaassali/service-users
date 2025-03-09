package com.poc.users.core.application.dto.command

import com.poc.users.core.domain.model.User
import com.poc.users.core.domain.valueobject.Mail
import com.poc.users.core.domain.valueobject.Password
import com.poc.users.core.domain.valueobject.UserRole
import java.util.*

data class CreateUserCommand(
    val identifier : UUID,
    val mail : String,
    val password : String,
    val encryptedPassword : String,
    val role : String,
) {
    fun toUser(): User {
        return User(
            identifier = identifier,
            mail = Mail(mail),
            password = Password(password, encryptedPassword),
            role = UserRole.valueOf(role.uppercase()),
        )
    }
}

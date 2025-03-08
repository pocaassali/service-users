package com.poc.users.core.application.dto.command

import com.poc.users.core.domain.model.User
import com.poc.users.core.domain.valueobject.Mail
import com.poc.users.core.domain.valueobject.Password
import com.poc.users.core.domain.valueobject.UserRole
import java.util.*

class UpdateUserCommand(
    private val identifier : String,
    val mail : String,
    val password : String,
    val hashedPassword : String,
    private val role : String,
) {

    fun toUser(): User {
        return User(
            identifier = UUID.fromString(identifier),
            mail = Mail(mail),
            password = Password("",hashedPassword),
            role = UserRole.valueOf(role),
        )
    }

}

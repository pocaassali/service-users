package com.poc.users.core.application.dto.command

import com.poc.users.core.domain.model.UserUpdateHelper
import com.poc.users.core.domain.valueobject.Mail
import com.poc.users.core.domain.valueobject.Password
import com.poc.users.core.domain.valueobject.UserRole
import java.util.*

class UpdateUserCommand(
    val identifier : UUID,
    val mail : String? = null,
    val password : String? = null,
    val hashedPassword : String? = null,
    val role : String? = null,
) {

    fun toUser(): UserUpdateHelper {
        return UserUpdateHelper(
            mail = Optional.ofNullable(mail?.let { Mail(it) }),
            password = Optional.ofNullable(hashedPassword?.let { Password.DEFAULT.copy(encryptedValue = it) }),
            role = Optional.ofNullable(role?.let { UserRole.valueOf(it) }),
        )
    }

}

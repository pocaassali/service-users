package com.poc.users.infra.api

import com.poc.users.core.domain.model.User

data class UserView(
    val identifier : String,
    val mail : String,
    val role : String,
) {
    companion object {
        fun from(user: User) : UserView {
            return UserView(
                identifier = user.identifier.toString(),
                mail = user.mail.value,
                role = user.role.name
            )
        }
    }
}

data class LoginResponse(
    val identifier : String,
    val password : String,
    val mail : String,
    val role : String,
) {
    companion object {
        fun from(user: User) : LoginResponse {
            return LoginResponse(
                identifier = user.identifier.toString(),
                password = user.password.encryptedValue,
                mail = user.mail.value,
                role = user.role.name
            )
        }
    }
}
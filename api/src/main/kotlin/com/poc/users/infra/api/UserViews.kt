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
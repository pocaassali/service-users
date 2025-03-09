package com.poc.users.core.application.dto.command

import com.poc.users.core.domain.model.User
import com.poc.users.core.domain.valueobject.UserRole
import com.poc.users.core.factories.aCreateUserCommand
import com.poc.users.core.factories.anUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class CreateUserCommandShould {

    @Test
    fun `correctly map CreateUserCommand to User`() {
        val createUserCommand = aCreateUserCommand()
        val expectedUser = anUser()

        val user: User = createUserCommand.toUser()

        assertThat(user).isEqualTo(expectedUser)
    }

    @Test
    fun `throw IllegalArgumentException for invalid role`() {
        val role = "INVALID_ROLE"

        val createUserCommand = aCreateUserCommand(role = role)

        assertThrows(IllegalArgumentException::class.java) {
            createUserCommand.toUser()
        }
    }

    @Test
    fun `correctly handle case-sensitive roles`() {
        val role = "admin"
        val createUserCommand = aCreateUserCommand(role = role)

        val user: User = createUserCommand.toUser()

        assertThat(user.role).isEqualTo(UserRole.ADMIN)
    }
}
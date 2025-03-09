package com.poc.users.core.application.dto.command

import com.poc.users.core.factories.anUpdateUserCommand
import com.poc.users.core.factories.anUserUpdateHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UpdateUserCommandShould {

    @Test
    fun `correctly map mail when provided`() {
        val mail = "test@example.com"
        val command = anUpdateUserCommand(mail = mail)
        val expectedUser = anUserUpdateHelper(mail = mail)

        val userHelper = command.toUser()

        assertThat(userHelper).isEqualTo(expectedUser)
    }

    @Test
    fun `return empty optional when nothing provided`() {
        val command = anUpdateUserCommand()

        val userHelper = command.toUser()

        assertThat(userHelper).isEqualTo(anUserUpdateHelper())
    }

    @Test
    fun `correctly map hashedPassword when provided`() {
        val hashedPassword = "hashed_password_value"
        val command = anUpdateUserCommand(hashedPassword = hashedPassword)
        val expectedUser = anUserUpdateHelper(password = hashedPassword)

        val userHelper = command.toUser()

        assertThat(userHelper).isEqualTo(expectedUser)
    }

    @Test
    fun `correctly map role when provided`() {
        val command = anUpdateUserCommand(role = "ADMIN")
        val expectedUser = anUserUpdateHelper(role = "ADMIN")

        val userHelper = command.toUser()

        assertThat(userHelper).isEqualTo(expectedUser)
    }
}
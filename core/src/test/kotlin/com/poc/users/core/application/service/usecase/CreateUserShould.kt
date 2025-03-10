package com.poc.users.core.application.service.usecase

import com.poc.users.core.domain.service.UserService
import com.poc.users.core.factories.aCreateUserCommand
import com.poc.users.core.factories.anUser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Optional

@ExtendWith(MockKExtension::class)
class CreateUserShould {

    @RelaxedMockK
    private lateinit var userService : UserService

    @InjectMockKs
    private lateinit var usecase : CreateUser

    @Test
    fun `should successfully create a user when valid command is provided`() {
        val command = aCreateUserCommand()
        val user = anUser()

        every { userService.createUser(any()) } returns Optional.of(user)

        val result = usecase.handle(command)

        assertThat(result).isPresent
        assertThat(result.get()).isEqualTo(user)
        verify { userService.createUser(user) }
    }

    @Test
    fun `should return empty optional when user creation fails`() {
        val command = aCreateUserCommand()
        every { userService.createUser(any()) } returns Optional.empty()

        val result = usecase.handle(command)

        assertThat(result).isNotPresent
        verify { userService.createUser(any()) }
        verify { userService.createUser(match { it.mail.value == command.mail }) }
    }
}
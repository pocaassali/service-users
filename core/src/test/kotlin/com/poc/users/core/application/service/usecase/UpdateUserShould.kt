package com.poc.users.core.application.service.usecase

import com.poc.users.core.domain.exception.UserCreationException
import com.poc.users.core.domain.service.UserService
import com.poc.users.core.factories.anUpdateUserCommand
import com.poc.users.core.factories.anUser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Optional

@ExtendWith(MockKExtension::class)
class UpdateUserShould {
    @RelaxedMockK
    private lateinit var service: UserService

    @InjectMockKs
    private lateinit var usecase: UpdateUser

    @Test
    fun `should return updated user when service successfully updates the user`() {
        val updatedUser = anUser()
        val command = anUpdateUserCommand()

        every { service.updateUser(user = command.toUser(), identifier = command.identifier) } returns Optional.of(updatedUser)

        val result = usecase.handle(command)

        assertThat(result).isPresent
        assertThat(result.get()).isEqualTo(updatedUser)
    }

    @Test
    fun `should return empty optional when user is not found`() {
        val command = anUpdateUserCommand()

        every { service.updateUser(user = command.toUser(), identifier = command.identifier) } returns Optional.empty()

        val result = usecase.handle(command)

        assertThat(result).isEmpty
    }

    @Test
    fun `should throw exception when userService updateUser throws exception`() {
        val invalidCommand = anUpdateUserCommand(identifier = "a2cbd57a-4103-4553-9986-b237e14bcb70")

        every {
            service.updateUser(
                user = invalidCommand.toUser(),
                identifier = invalidCommand.identifier
            )
        } throws UserCreationException("User not found !")

        val exception = assertThrows(UserCreationException::class.java) {
            usecase.handle(invalidCommand)
        }
        assertEquals("User not found !", exception.message)
    }
}
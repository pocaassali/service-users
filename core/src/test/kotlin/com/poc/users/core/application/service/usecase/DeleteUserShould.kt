package com.poc.users.core.application.service.usecase

import com.poc.users.core.application.dto.command.DeleteUserCommand
import com.poc.users.core.domain.service.UserService
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class DeleteUserShould {

    @RelaxedMockK
    private lateinit var service: UserService

    @InjectMockKs
    private lateinit var useCase: DeleteUser

    @Test
    fun `call deleteUser on service with correct UUID`() {
        val userId = UUID.randomUUID()
        val command = DeleteUserCommand(id = userId.toString())
        every { service.deleteUser(userId) } just Runs
        useCase.handle(command)

        verify(exactly = 1) { service.deleteUser(userId) }
    }

    @Test
    fun `not throw exception when service does not find user`() {
        val userId = UUID.randomUUID()
        val command = DeleteUserCommand(id = userId.toString())

        every { service.deleteUser(userId) } just Runs
        useCase.handle(command)

        verify(exactly = 1) { service.deleteUser(userId) }
    }

    @Test
    fun `throw exception when invalid UUID is passed`() {
        val invalidCommand = DeleteUserCommand(id = "invalid-uuid")

        assertThrows(IllegalArgumentException::class.java) {
            useCase.handle(invalidCommand)
        }
        verify(exactly = 0) { service.deleteUser(any()) }
    }
}
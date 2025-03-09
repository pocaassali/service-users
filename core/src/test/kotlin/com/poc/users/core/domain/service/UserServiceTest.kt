package com.poc.users.core.domain.service

import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.domain.exception.UserCreationException
import com.poc.users.core.domain.exception.UserNotFoundException
import com.poc.users.core.factories.anUser
import com.poc.users.core.factories.anUserUpdateHelper
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class UserServiceTest {

    @RelaxedMockK
    private lateinit var mockUsers: Users

    @InjectMockKs
    private lateinit var service: UserService

    @Test
    fun `createUser should save valid user`() {
        val user = anUser()
        val expectedResult = Optional.of(user)

        every { mockUsers.findById(user.identifier) } returns Optional.empty()
        every { mockUsers.findByMail(user.mail) } returns Optional.empty()
        every { mockUsers.save(user) } returns expectedResult

        val result = service.createUser(user)

        assertThat(result).isEqualTo(expectedResult)
        verify { mockUsers.save(user) }
    }

    @Test
    fun `createUser should throw UserCreationException if user ID already exists`() {
        val user = anUser()

        every { mockUsers.findById(user.identifier) } returns Optional.of(user)

        assertThrows(UserCreationException::class.java) {
            service.createUser(user)
        }

    }

    @Test
    fun `createUser should throw UserCreationException if email already exists`() {
        val user = anUser()
        every { mockUsers.findById(user.identifier) } returns Optional.empty()
        every { mockUsers.findByMail(user.mail) } returns Optional.of(user)


        assertThrows(UserCreationException::class.java) {
            service.createUser(user)
        }
    }

    @Test
    fun `updateUser should update existing user`() {
        val originalUser = anUser()
        val updateHelper = anUserUpdateHelper(mail = "updated@mail.com")
        val updatedUser = originalUser.updateWith(updateHelper)
        val identifier = originalUser.identifier
        val expectedResult = Optional.of(updatedUser)
        every { mockUsers.findById(identifier) } returns Optional.of(originalUser)
        every { mockUsers.update(updatedUser) } returns expectedResult

        val result = service.updateUser(updateHelper, identifier)

        assertThat(result).isEqualTo(expectedResult)
        verify { mockUsers.update(updatedUser) }
    }

    @Test
    fun `updateUser should throw UserNotFoundException if user does not exist`() {
        val updateHelper = anUserUpdateHelper(mail = "updated@mail.com")
        val identifier = UUID.randomUUID()
        every { mockUsers.findById(identifier) } returns Optional.empty()


        assertThrows(UserNotFoundException::class.java) {
            service.updateUser(updateHelper, identifier)
        }
    }

    @Test
    fun `deleteUser should delete existing user`() {
        val identifier = UUID.randomUUID()
        every { mockUsers.delete(identifier) } returns true

        service.deleteUser(identifier)

        verify { mockUsers.delete(identifier) }
    }

    @Test
    fun `deleteUser should throw UserNotFoundException if user does not exist`() {
        val identifier = UUID.randomUUID()
        every { mockUsers.delete(identifier) } returns false

        assertThrows(UserNotFoundException::class.java) {
            service.deleteUser(identifier)
        }
    }
}
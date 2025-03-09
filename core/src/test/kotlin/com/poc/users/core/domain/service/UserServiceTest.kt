package com.poc.users.core.domain.service

import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.domain.exception.UserCreationException
import com.poc.users.core.domain.exception.UserNotFoundException
import com.poc.users.core.domain.model.User
import com.poc.users.core.domain.model.UserUpdateHelper
import com.poc.users.core.domain.valueobject.Mail
import com.poc.users.core.domain.valueobject.Password
import com.poc.users.core.domain.valueobject.UserRole
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class UserServiceTest {

    /**
     * Tests for UserService.createUser.
     */
    @Test
    fun `createUser should save valid user`() {
        val mockUsers = mockk<Users>()
        val user = User(identifier = UUID.randomUUID(), mail = Mail("test@mail.com"), password = Password("<PaSSW0RD>!", "<PASSWORD>"), role = UserRole.USER)
        val expectedResult = Optional.of(user)

        every { mockUsers.findById(user.identifier) } returns Optional.empty()
        every { mockUsers.findByMail(user.mail) } returns Optional.empty()
        every { mockUsers.save(user) } returns expectedResult

        val userService = UserService(mockUsers)
        val result = userService.createUser(user)

        assertEquals(expectedResult, result)
        verify { mockUsers.save(user) }
    }

    @Test
    fun `createUser should throw UserCreationException if user ID already exists`() {
        val mockUsers = mockk<Users>()
        val user = User(identifier = UUID.randomUUID(), mail = Mail("test@mail.com"), password = Password("<PaSSW0RD>!", "<PASSWORD>"), role = UserRole.USER)

        every { mockUsers.findById(user.identifier) } returns Optional.of(user)

        val userService = UserService(mockUsers)

        assertThrows(UserCreationException::class.java) {
            userService.createUser(user)
        }
    }

    @Test
    fun `createUser should throw UserCreationException if email already exists`() {
        val mockUsers = mockk<Users>()
        val user = User(identifier = UUID.randomUUID(), mail = Mail("test@mail.com"), password = Password("<PaSSW0RD>!", "<PASSWORD>"), role = UserRole.USER)

        every { mockUsers.findById(user.identifier) } returns Optional.empty()
        every { mockUsers.findByMail(user.mail) } returns Optional.of(user)

        val userService = UserService(mockUsers)

        assertThrows(UserCreationException::class.java) {
            userService.createUser(user)
        }
    }

    /**
     * Tests for UserService.updateUser.
     */
    @Test
    fun `updateUser should update existing user`() {
        val mockUsers = mockk<Users>()
        val originalUser = User(identifier = UUID.randomUUID(), mail = Mail("test@mail.com"), password = Password("<PaSSW0RD>!", "<PASSWORD>"), role = UserRole.USER)
        val updateHelper = UserUpdateHelper(mail = Optional.of(Mail("updated@mail.com")), password = Optional.empty(), role = Optional.empty())
        val updatedUser = originalUser.updateWith(updateHelper)
        val identifier = originalUser.identifier
        val expectedResult = Optional.of(updatedUser)

        every { mockUsers.findById(identifier) } returns Optional.of(originalUser)
        every { mockUsers.update(updatedUser) } returns expectedResult

        val userService = UserService(mockUsers)
        val result = userService.updateUser(updateHelper, identifier)

        assertEquals(expectedResult, result)
        verify { mockUsers.update(updatedUser) }
    }

    @Test
    fun `updateUser should throw UserNotFoundException if user does not exist`() {
        val mockUsers = mockk<Users>()
        val updateHelper = UserUpdateHelper(mail = Optional.of(Mail("updated@mail.com")), password = Optional.empty(), role = Optional.empty())
        val identifier = UUID.randomUUID()

        every { mockUsers.findById(identifier) } returns Optional.empty()

        val userService = UserService(mockUsers)

        assertThrows(UserNotFoundException::class.java) {
            userService.updateUser(updateHelper, identifier)
        }
    }

    /**
     * Tests for UserService.deleteUser.
     */
    @Test
    fun `deleteUser should delete existing user`() {
        val mockUsers = mockk<Users>()
        val identifier = UUID.randomUUID()

        every { mockUsers.delete(identifier) } returns true

        val userService = UserService(mockUsers)
        userService.deleteUser(identifier)

        verify { mockUsers.delete(identifier) }
    }

    @Test
    fun `deleteUser should throw UserNotFoundException if user does not exist`() {
        val mockUsers = mockk<Users>()
        val identifier = UUID.randomUUID()

        every { mockUsers.delete(identifier) } returns false

        val userService = UserService(mockUsers)

        assertThrows(UserNotFoundException::class.java) {
            userService.deleteUser(identifier)
        }
    }
}
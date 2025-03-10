package com.poc.users.core.application.service

import com.poc.users.core.application.dto.command.DeleteUserCommand
import com.poc.users.core.application.dto.query.GetUserByCredentialsQuery
import com.poc.users.core.application.dto.query.GetUserByIdQuery
import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.application.service.usecase.*
import com.poc.users.core.domain.service.UserService
import com.poc.users.core.factories.aCreateUserCommand
import com.poc.users.core.factories.anAdmin
import com.poc.users.core.factories.anUpdateUserCommand
import com.poc.users.core.factories.anUser
import com.poc.users.core.fakes.FakeUserRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class UserApplicationServiceImplShould {

    @RelaxedMockK
    private lateinit var getAllUsers: GetAllUsers

    @RelaxedMockK
    private lateinit var getUserById: GetUserById

    @RelaxedMockK
    private lateinit var createUser: CreateUser

    @RelaxedMockK
    private lateinit var updateUser: UpdateUser

    @RelaxedMockK
    private lateinit var deleteUser: DeleteUser

    @RelaxedMockK
    private lateinit var getUserByCredentials: GetUserByCredentials

    @InjectMockKs
    private lateinit var service: UserApplicationServiceImpl

    @Test
    fun `getAllUsers should return list of users`() {
        val userList = listOf(anUser(), anAdmin())
        every { getAllUsers.execute() } returns listOf(anUser(), anAdmin())

        val result = service.getAllUsers()



        assertEquals(userList, result)
        verify { getAllUsers.execute() }
    }

    @Test
    fun `getUserById should return user when found`() {
        val query = GetUserByIdQuery(UUID.randomUUID())
        val user = anUser()
        every { getUserById.handle(query) } returns Optional.of(user)

        val result = service.getUserById(query)

        assertThat(result).isPresent
        assertThat(result.get()).isEqualTo(user)
        verify { getUserById.handle(query) }
    }

    @Test
    fun `createUser should create and return user`() {
        val command = aCreateUserCommand()
        val user = anUser()
        every { createUser.handle(command) } returns Optional.of(user)


        val result = service.createUser(command)

        assertThat(result).isPresent
        assertThat(result.get()).isEqualTo(user)
        verify { createUser.handle(command) }
    }

    @Test
    fun `updateUser should update and return user`() {
        val command = anUpdateUserCommand()
        val updatedUser = anUser()
        every { updateUser.handle(command) } returns Optional.of(updatedUser)

        val result = service.updateUser(command)

        assertThat(result).isPresent
        assertThat(result.get()).isEqualTo(updatedUser)
        verify { updateUser.handle(command) }
    }

    @Test
    fun `deleteUser should call delete handler`() {
        val command = DeleteUserCommand(UUID.randomUUID().toString())
        every { deleteUser.handle(command) } returns Unit

        service.deleteUser(command)

        verify { deleteUser.handle(command) }
    }

    @Test
    fun `getUserByCredentials should return user when credentials are valid`() {
        val query = GetUserByCredentialsQuery("test-user@mail.com")
        val user = anUser()
        every { getUserByCredentials.handle(query) } returns Optional.of(user)

        val result = service.getUserByCredentials(query)

        assertThat(result).isPresent
        assertThat(result.get()).isEqualTo(user)
        verify { getUserByCredentials.handle(query) }
    }
}
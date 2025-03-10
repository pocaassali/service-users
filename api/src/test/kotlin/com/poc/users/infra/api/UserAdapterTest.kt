package com.poc.users.infra.api

import com.poc.users.core.application.dto.command.DeleteUserCommand
import com.poc.users.core.application.dto.query.GetUserByCredentialsQuery
import com.poc.users.core.application.dto.query.GetUserByIdQuery
import com.poc.users.core.application.ports.input.UserApplicationService
import com.poc.users.core.domain.model.User
import com.poc.users.factories.*
import com.poc.users.infra.api.utils.UUIDGenerator
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@ExtendWith(MockKExtension::class)
class UserAdapterTest {

    @RelaxedMockK
    private lateinit var applicationService: UserApplicationService

    @RelaxedMockK
    private lateinit var passwordEncoder: PasswordEncoder

    @RelaxedMockK
    private lateinit var uuidGenerator: UUIDGenerator

    @InjectMockKs
    private lateinit var adapter: UserAdapter

    @Test
    fun `getAllUsers should return a list of users`() {
        val user1 = anUser()
        val user2 = anUser()
        every { applicationService.getAllUsers() } returns listOf(user1, user2)

        val result = adapter.getAllUsers()

        assertThat(result).containsExactlyInAnyOrder(UserView.from(user1), UserView.from(user2))
    }

    @Test
    fun `getUserById should return a user when found`() {
        val user = anUser()
        every { applicationService.getUserById(GetUserByIdQuery(user.identifier)) } returns Optional.of(user)

        val result = adapter.getUserById(user.identifier.toString())

        assertThat(result).isEqualTo(anUserView())
    }

    @Test
    fun `getUserById should return null when user not found`() {
        val unknownId = UUID.randomUUID()
        every { applicationService.getUserById(GetUserByIdQuery(unknownId)) } returns Optional.empty()

        val result = adapter.getUserById(unknownId.toString())

        assertThat(result).isNull()
    }

    @Test
    fun `create should return created user`() {
        val request = anUserCreationRequest()
        val encryptedPassword = "encryptedPassword"
        val createdUser = anUser()

        every { passwordEncoder.encode(request.password) } returns encryptedPassword
        every { uuidGenerator.generate() } returns UUID.fromString("a2cbd57a-4103-4553-9986-b237e14bcb72")
        every {
            applicationService.createUser(
                request.toCommand(UUID.fromString("a2cbd57a-4103-4553-9986-b237e14bcb72"),encryptedPassword)
            ) } returns Optional.of(createdUser)

        val result = adapter.create(request)

        assertThat(result).isEqualTo(anUserView())
    }

    //TODO : Fix this test
    @Test
    //@Disabled
    fun `update should return updated user`() {
        val userId = "a2cbd57a-4103-4553-9986-b237e14bcb72"
        val newMail = "new@mail.com"
        val request = anUserEditionRequest(mail = newMail)
        val expectedUser = anUser(identifier = userId, mail = newMail)
        val expectedUserView = anUserView(mail = newMail)

        every {
            applicationService.updateUser(request.toCommand(
                identifier = UUID.fromString(userId),
                hashedPassword = null)
            )
        } returns Optional.of(expectedUser)

        val result = adapter.update(userId, request)

        assertThat(result).isEqualTo(expectedUserView)
    }


    @Test
    fun `delete should call application service`() {
        val userId = UUID.randomUUID().toString()

        adapter.delete(userId)

        verify(exactly = 1) { applicationService.deleteUser(DeleteUserCommand(userId)) }
    }

    @Test
    fun `getUserByCredentials should return user when credentials valid`() {
        val request = anUserLoginRequest()
        val user = anUser()

        every {
            applicationService.getUserByCredentials(GetUserByCredentialsQuery(request.mail))
        } returns Optional.of(user)

        val result = adapter.getUserByCredentials(request)

        assertThat(result).isEqualTo(LoginResponse.from(user))
    }

    @Test
    fun `getUserByCredentials should return null when credentials invalid`() {
        val request = anUserLoginRequest(mail = "invalid@mail.com")

        every {
            applicationService.getUserByCredentials(GetUserByCredentialsQuery(request.mail))
        } returns Optional.empty()

        val result = adapter.getUserByCredentials(request)

        assertThat(result).isNull()

    }

    @Test
    fun `should map correctly an optional of user to an user view`(){
        val optionalUser = Optional.of(anUser())

        assertThat(optionalUser.getOrNull()).isEqualTo(anUserView())
    }

    @Test
    fun `should map an empty optional of user to a null user view`(){
        val optionalUser = Optional.empty<User>()

        assertThat(optionalUser.getOrNull()).isNull()
    }
}




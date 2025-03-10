package com.poc.users.core.application.service.usecase

import com.poc.users.core.application.dto.query.GetUserByCredentialsQuery
import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.domain.exception.UserCreationException
import com.poc.users.core.domain.valueobject.Mail
import com.poc.users.core.factories.anUser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Optional

@ExtendWith(MockKExtension::class)
class GetUserByCredentialsShould {

    @RelaxedMockK
    private lateinit var users: Users

    @InjectMockKs
    private lateinit var useCase: GetUserByCredentials

    @Test
    fun `return user when user with the given mail exists`() {
        val mail = Mail("user@mail.com")
        val user = anUser()
        val query = GetUserByCredentialsQuery("user@mail.com")

        every { users.findByMail(mail) } returns Optional.of(user)

        val result = useCase.handle(query)

        assertThat(result).isPresent
        assertThat(result.get()).isEqualTo(user)

        verify(exactly = 1) { users.findByMail(mail) }
    }

    @Test
    fun `return empty when user with the given mail does not exist`() {
        val mail = Mail("nonexistent@example.com")
        val query = GetUserByCredentialsQuery("nonexistent@example.com")

        every { users.findByMail(mail) } returns Optional.empty()

        val result = useCase.handle(query)

        assertThat(result).isEmpty()
        verify(exactly = 1) { users.findByMail(mail) }
    }

    @Test
    fun `handle invalid mail format gracefully`() {
        val invalidMail = "invalid-email"
        val query = GetUserByCredentialsQuery(invalidMail)

        val exception = assertThrows(UserCreationException::class.java) {
            useCase.handle(query)
        }


        assertThat(exception.message).isEqualTo(Mail.WRONG_FORMAT_EMAIL)
    }
}
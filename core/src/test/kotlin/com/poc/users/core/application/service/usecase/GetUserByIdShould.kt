package com.poc.users.core.application.service.usecase

import com.poc.users.core.application.dto.query.GetUserByIdQuery
import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.factories.anUser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Optional
import java.util.UUID

@ExtendWith(MockKExtension::class)
class GetUserByIdShould {

    @RelaxedMockK
    private lateinit var users: Users
    @InjectMockKs
    private lateinit var useCase: GetUserById

    @Test
    fun `should return user when user with id exists`() {
        val user = anUser()
        every { users.findById(user.identifier) } returns Optional.of(user)

        val query = GetUserByIdQuery(user.identifier)

        val result = useCase.handle(query)

        assertThat(result).isPresent
        assertThat(result.get()).isEqualTo(user)
    }

    @Test
    fun `should return empty when user with id does not exist`() {
        val nonExistingUserId = UUID.randomUUID()
        every { users.findById(nonExistingUserId) } returns Optional.empty()

        val query = GetUserByIdQuery(nonExistingUserId)

        val result = useCase.handle(query)

        assertThat(result).isEmpty
    }
}
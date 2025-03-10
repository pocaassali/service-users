package com.poc.users.core.application.service.usecase

import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.factories.anAdmin
import com.poc.users.core.factories.anUser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetAllUsersShould {
    @RelaxedMockK
    private lateinit var mockUsersRepository : Users
    @InjectMockKs
    private lateinit var useCase : GetAllUsers

    @Test
    fun `return a list of users when repository has users`() {
        val mockUserList = listOf(
            anUser(),
            anAdmin()
        )

        every { mockUsersRepository.findAll() } returns mockUserList

        val result = useCase.execute()

        assertThat(result).containsExactlyInAnyOrder(mockUserList[0], mockUserList[1])
    }

    @Test
    fun `should return an empty list when repository has no users`() {
        every { mockUsersRepository.findAll() } returns emptyList()

        val result = useCase.execute()

        assertThat(result).isEmpty()
    }
}
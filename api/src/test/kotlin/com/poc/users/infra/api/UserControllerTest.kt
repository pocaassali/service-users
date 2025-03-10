package com.poc.users.infra.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.poc.users.infra.api.utils.JwtUtil
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(UserController::class)
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var userAdapter: UserAdapter

    @MockkBean
    lateinit var jwtUtil: JwtUtil

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    @WithMockUser
    fun `test createUser`() {
        val request = UserCreationRequest(mail = "john.doe@example.com", password = "P@ssw0rd", role = "USER")
        val userView = UserView(identifier = "a2cbd57a-4103-4553-9986-b237e14bcb72", mail = "john.doe@example.com", role = "USER")

        every { userAdapter.create(request) } returns userView

        mockMvc.perform(
            post("/users")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.identifier").value("a2cbd57a-4103-4553-9986-b237e14bcb72"))
            .andExpect(jsonPath("$.mail").value("john.doe@example.com"))
    }

    @Test
    @WithMockUser
    fun `test getUsers`() {
        val users = listOf(
            UserView(identifier = "a2cbd57a-4103-4553-9986-b237e14bcb72", mail = "alice@example.com", role = "USER"),
            UserView(identifier = "a2cbd57a-4103-4553-9986-b237e14bcb73", mail = "bob@example.com", role = "USER")
        )

        every { userAdapter.getAllUsers()} returns users

        mockMvc.perform(get("/users"))
            .andExpect(status().isOk)
    }

    @Test
    @WithMockUser
    fun `should send 200 when get user by id`() {
        val user = UserView(identifier = "a2cbd57a-4103-4553-9986-b237e14bcb72", mail = "john.doe@example.com", role = "USER")

        every { userAdapter.getUserById("a2cbd57a-4103-4553-9986-b237e14bcb72") } returns user

        mockMvc.perform(
            get("/users/a2cbd57a-4103-4553-9986-b237e14bcb72")
                .with(csrf())
        )
            .andExpect(status().isOk)

        verify(exactly = 1) { userAdapter.getUserById("a2cbd57a-4103-4553-9986-b237e14bcb72") }
    }


    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `test updateUser`() {
        val request = UserEditionRequest(mail = "update@mail.com", password = null, role = null)
        val updatedUser = UserView(identifier = "a2cbd57a-4103-4553-9986-b237e14bcb72", mail = "update@mail.com", role = "USER")

        every { userAdapter.update("a2cbd57a-4103-4553-9986-b237e14bcb72", request) } returns updatedUser

        mockMvc.perform(
            put("/users/a2cbd57a-4103-4553-9986-b237e14bcb72")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.mail").value("update@mail.com"))
    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `test deleteUser`() {
        every { userAdapter.delete("1") } just runs

        mockMvc.perform(
            delete("/users/1")
            .with(csrf())
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.message").value("User with id: 1 has been deleted"))
    }

    @Test
    @WithMockUser
    fun `test getUserByCredentials`() {
        val credentials = UserLoginRequest("john.doe@example.com")
        val loginResponse = LoginResponse(identifier = "a2cbd57a-4103-4553-9986-b237e14bcb72", password = "", mail = "john.doe@example.com", role = "USER")

        every { userAdapter.getUserByCredentials(credentials) } returns loginResponse

        mockMvc.perform(
            post("/users/credentials")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(credentials))
        )
            .andExpect(status().isOk)
    }
}
package com.poc.users.core.application.ports.input

import com.poc.users.core.application.dto.command.CreateUserCommand
import com.poc.users.core.application.dto.command.DeleteUserCommand
import com.poc.users.core.application.dto.command.UpdateUserCommand
import com.poc.users.core.application.dto.query.GetUserByCredentialsQuery
import com.poc.users.core.application.dto.query.GetUserByIdQuery
import com.poc.users.core.domain.model.User
import java.util.*


interface UserApplicationService {
    fun getAllUsers(): List<User>
    fun getUserById(query: GetUserByIdQuery): Optional<User>
    fun createUser(command: CreateUserCommand): Optional<User>
    fun updateUser(command: UpdateUserCommand): Optional<User>
    fun deleteUser(command: DeleteUserCommand)
    fun getUserByCredentials(query : GetUserByCredentialsQuery): Optional<User>
}
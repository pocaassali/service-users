package com.poc.users.core.application.ports.input

import com.poc.users.core.application.dto.command.CreateUserCommand
import com.poc.users.core.application.dto.command.DeleteUserCommand
import com.poc.users.core.application.dto.command.UpdateUserCommand
import com.poc.users.core.application.dto.query.GetUserByIdQuery
import com.poc.users.core.domain.model.User


interface UserApplicationService {
    fun getAllUsers(): List<User>
    fun getUserById(query: GetUserByIdQuery): User?
    fun createUser(command: CreateUserCommand): User?
    fun updateUser(command: UpdateUserCommand): User?
    fun deleteUser(command: DeleteUserCommand)
}
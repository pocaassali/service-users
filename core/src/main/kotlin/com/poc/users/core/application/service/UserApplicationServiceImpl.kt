package com.poc.users.core.application.service

import com.poc.users.core.application.dto.command.CreateUserCommand
import com.poc.users.core.application.dto.command.DeleteUserCommand
import com.poc.users.core.application.dto.command.UpdateUserCommand
import com.poc.users.core.application.dto.query.GetAllUsersQuery
import com.poc.users.core.application.dto.query.GetUserByCredentialsQuery
import com.poc.users.core.application.dto.query.GetUserByIdQuery
import com.poc.users.core.application.ports.input.UserApplicationService
import com.poc.users.core.ddd.ApplicationService
import com.poc.users.core.domain.model.User
import java.util.*

@ApplicationService
class UserApplicationServiceImpl(
    private val createUser : CreateUser,
    private val getAllUsers : GetAllUsers,
    private val getUserById : GetUserById,
    private val updateUser : UpdateUser,
    private val deleteUser : DeleteUser,
    private val getUserByCredentials : GetUserByCredentials,
) : UserApplicationService {
    override fun getAllUsers(): List<User> {
        return getAllUsers.execute()
    }

    override fun getUserById(query: GetUserByIdQuery): Optional<User> {
        return getUserById.handle(query)
    }

    override fun createUser(command: CreateUserCommand): Optional<User> {
        return createUser.handle(command)
    }

    override fun updateUser(command: UpdateUserCommand): Optional<User> {
        return updateUser.handle(command)
    }

    override fun deleteUser(command: DeleteUserCommand) {
        deleteUser.handle(command)
    }

    override fun getUserByCredentials(query: GetUserByCredentialsQuery): Optional<User> {
        return getUserByCredentials.handle(query)
    }
}
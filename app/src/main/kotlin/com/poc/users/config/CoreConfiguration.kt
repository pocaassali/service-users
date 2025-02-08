package com.poc.users.config

import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.application.service.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/*
@Configuration
@ComponentScan(
    basePackages = ["com.poc.users"],
    includeFilters = [Filter(
        type = ANNOTATION,
        value = [DomainService::class, Stub::class, AntiCorruptionLayer::class, Usecase::class, Adapter::class]
    )]
)
class CoreConfiguration*/
@Configuration
class CoreConfiguration {

    @Bean
    fun userApplicationConfig(
        createUser: CreateUser,
        getAllUsers: GetAllUsers,
        getUserById: GetUserById,
        updateUser: UpdateUser,
        deleteUser: DeleteUser,
    ) = UserApplicationServiceImpl(
        createUser = createUser,
        getAllUsers = getAllUsers,
        getUserById = getUserById,
        updateUser = updateUser,
        deleteUser = deleteUser
    )

    @Bean
    fun createUser(users: Users) = CreateUser(users = users)

    @Bean
    fun getAllUsers(users: Users) = GetAllUsers(users = users)

    @Bean
    fun getUserById(users: Users) = GetUserById(users = users)

    @Bean
    fun updateUser(users: Users) = UpdateUser(users = users)

    @Bean
    fun deleteUser(users: Users) = DeleteUser(users = users)
}
package com.poc.users.core.application.service

import com.poc.users.core.application.dto.query.GetAllUsersQuery
import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.domain.model.User


class GetAllUsers(
    private val users: Users
) : AbstractQueryHandler<GetAllUsersQuery,List<User>>() {
    override fun execute(query: GetAllUsersQuery) : List<User> {
        return users.findAll()
    }
}

package com.poc.users.core.application.service

import com.poc.users.core.application.dto.query.GetUserByIdQuery
import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.domain.model.User

class GetUserById(
    private val users: Users
) : AbstractQueryHandler<GetUserByIdQuery, User?>() {
    override fun execute(query: GetUserByIdQuery) : User? {
        return users.findById(query.id)
    }
}

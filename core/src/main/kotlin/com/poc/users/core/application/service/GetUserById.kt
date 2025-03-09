package com.poc.users.core.application.service

import com.poc.users.core.application.dto.query.GetUserByIdQuery
import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.ddd.Usecase
import com.poc.users.core.domain.model.User
import java.util.Optional

@Usecase
class GetUserById(private val users: Users) : AbstractQueryHandler<GetUserByIdQuery, Optional<User>>() {

    override fun execute(query: GetUserByIdQuery) : Optional<User> {
        return users.findById(query.id)
    }

}

package com.poc.users.core.application.service

import com.poc.users.core.application.dto.query.GetUserByCredentialsQuery
import com.poc.users.core.application.dto.query.GetUserByIdQuery
import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.ddd.Usecase
import com.poc.users.core.domain.model.User
import com.poc.users.core.domain.valueobject.Mail
import java.util.Optional

@Usecase
class GetUserByCredentials(
    private val users: Users
) : AbstractQueryHandler<GetUserByCredentialsQuery, Optional<User>>() {

    override fun execute(query: GetUserByCredentialsQuery) : Optional<User> {
        return users.findByMail(Mail(query.mail))
    }

}

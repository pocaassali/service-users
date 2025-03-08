package com.poc.users.core.application.ports.output

import com.poc.users.core.domain.model.User
import com.poc.users.core.domain.valueobject.Mail
import com.poc.users.core.domain.valueobject.Password
import java.util.*

interface Users {
    fun save(user: User): Optional<User>
    fun findAll(): List<User>
    fun findById(id: UUID): Optional<User>
    fun findByMail(mail: Mail): Optional<User>
    fun update(user: User): Optional<User>
    fun delete(id: UUID)
}
package com.poc.users.core.application.ports.output

import com.poc.users.core.domain.model.User
import java.util.*

interface Users {
    fun save(user: User): User?
    fun findAll(): List<User>
    fun findById(id: UUID): User?
    fun update(user: User): User?
    fun delete(id: UUID)
}
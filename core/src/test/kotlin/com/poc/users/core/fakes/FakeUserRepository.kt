package com.poc.users.core.fakes

import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.domain.model.User
import com.poc.users.core.domain.valueobject.Mail
import com.poc.users.core.factories.anAdmin
import com.poc.users.core.factories.anUser
import java.util.*

class FakeUserRepository : Users {

    private val users = mutableMapOf(
        Pair(
            1L, anUser()
        ),
        Pair(
            2L, anAdmin()
        )
    )

    override fun save(user: User): Optional<User> {
        val id = (users.size + 1).toLong()
        return Optional.ofNullable(users[id])
    }

    override fun findAll(): List<User> {
        return users.values.toList()
    }

    override fun findById(id: UUID): Optional<User> {
        return Optional.ofNullable(users.values.find { it.identifier == id })
    }

    override fun findByMail(mail: Mail): Optional<User> {
        return Optional.ofNullable(users.values.find { it.mail == mail })
    }

    override fun update(user: User): Optional<User> {
        val userToUpdate = users.entries.find { it.value.identifier == user.identifier }
        return Optional.ofNullable(users[userToUpdate?.key])
    }

    override fun delete(id: UUID): Boolean {
        val userToDelete = users.entries.find { it.value.identifier == id }
        userToDelete?.let { users.remove(it.key) }
        return userToDelete != null
    }
}
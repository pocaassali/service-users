package com.poc.users.infra.persistence

import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.domain.model.User
import com.poc.users.core.domain.valueobject.Mail
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InMemoryUserRepository : Users {

    private val users = mutableMapOf(
        Pair(1L, UserEntity(
                identifier = "bd657168-e573-4925-900a-d5f26e82760b",
                mail = "alice@mail.fr",
                password = "\$2a\$10\$h98/Ebg03XPpvGZA9sujauaxPpr.YoIfNCw/WpGO/tae.tbSdoNZi",
                role = "ADMIN"
            )
        ),
        Pair(2L, UserEntity(
            identifier = "40c273b3-c4fc-4227-9523-e4782a7f2c20",
            mail = "bob@mail.com",
            password = "\$2a\$10\$HTEK6eWAfx3iCApN1cZ1Y.r8juYvhkvg2SRDfQXJwBOBe7ujAsuTa",
            role = "USER"
        ))
    )

    override fun save(user: User): Optional<User> {
        val id = (users.size+1).toLong()
        users[id] = UserEntity.from(user)
        println("new user ${users[id]}")
        return Optional.ofNullable(users[id]?.toUser())
    }

    override fun findAll(): List<User> {
        return users.map { it.value.toUser() }
    }

    override fun findById(id: UUID): Optional<User> {
        return Optional.ofNullable(users.values.find { it.identifier == id.toString() }?.toUser())
    }

    override fun findByMail(mail: Mail): Optional<User> {
        return Optional.ofNullable(users.values.find { it.mail == mail.value }?.toUser())
    }

    override fun update(user: User): User? {
        val userToUpdate = users.entries.find { UUID.fromString(it.value.identifier) == user.identifier }
        userToUpdate?.let { users[it.key] = UserEntity.from(user) }
        return users[userToUpdate?.key]?.toUser()
    }

    override fun delete(id: UUID) {
        val userToDelete = users.entries.find { UUID.fromString(it.value.identifier) == id }
        userToDelete?.let { users.remove(it.key) }
    }
}
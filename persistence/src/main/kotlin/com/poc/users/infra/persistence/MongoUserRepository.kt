package com.poc.users.infra.persistence

import com.poc.users.core.application.ports.output.Users
import com.poc.users.core.domain.model.User
import com.poc.users.core.domain.valueobject.Mail
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MongoUserRepository(
    private val repository: UserRepository
) : Users {
    override fun save(user: User): Optional<User> {
        val entity = UserDocument.from(user)
        return Optional.ofNullable(repository.save(entity).toUser())
    }

    override fun findAll(): List<User> {
        return repository.findAll().map { it.toUser() }
    }

    override fun findById(id: UUID): Optional<User> {
        return Optional.ofNullable(repository.findByIdentifier(id.toString())?.toUser())
    }

    override fun findByMail(mail: Mail): Optional<User> {
        return Optional.ofNullable(repository.findByMail(mail.value)?.toUser())
    }

    override fun update(user: User): Optional<User> {
        val findUser = repository.findByIdentifier(user.identifier.toString()) ?: return Optional.empty()
        val userToUpdate = UserDocument.from(user).copy(id = findUser.id)
        return Optional.of(repository.save(userToUpdate).toUser())
    }

    override fun delete(id: UUID) {
        val findUser = repository.findByIdentifier(id.toString()) ?: throw NoSuchElementException()
        repository.delete(findUser)
    }
}
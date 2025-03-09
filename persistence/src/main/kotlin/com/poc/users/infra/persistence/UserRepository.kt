package com.poc.users.infra.persistence

import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<UserDocument, String>{
    fun findByMail(mail: String): UserDocument?
    fun findByIdentifier(identifier: String): UserDocument?
}
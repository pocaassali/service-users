package com.poc.users.infra.api.utils

import org.springframework.stereotype.Component
import java.util.*

interface UUIDGenerator {
    fun generate(): UUID
}

@Component
class DefaultUUIDGenerator : UUIDGenerator {
    override fun generate(): UUID = UUID.randomUUID()
}
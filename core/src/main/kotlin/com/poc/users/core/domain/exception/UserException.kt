package com.poc.users.core.domain.exception

open class UserException(message: String) : DomainException(message)

class UserCreationException(message: String) : UserException(message)
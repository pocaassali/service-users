package com.poc.users.core.application.service.usecase

abstract class AbstractQueryHandler<Q,R> {
    protected abstract fun execute(query: Q): R

    fun handle(query: Q): R {
        return execute(query)
    }
}
package com.poc.users.infra.api.security.guard

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SecurityGuard {

    fun hasAdminRole() = isAdmin()

    fun isSelf(userId: String): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication.principal == userId || isAdmin()
    }

    /*fun hasRole(role: String): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication.authorities.any { it.authority == "ROLE_$role" }
    }*/

    private fun isAdmin(): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication.authorities.any { it.authority == "ROLE_ADMIN" }
    }
}
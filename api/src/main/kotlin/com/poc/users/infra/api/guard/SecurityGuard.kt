package com.poc.users.infra.api.guard

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class SecurityGuard {

    fun isSelfOrAdmin(userId: String): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication.principal == userId || authentication.authorities.any { it.authority == "ROLE_ADMIN" }
    }

    fun hasRole(role: String): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication.authorities.any { it.authority == "ROLE_$role" }
    }

    fun hasAdminRole(): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication.authorities.any { it.authority == "ROLE_ADMIN" }
    }
}
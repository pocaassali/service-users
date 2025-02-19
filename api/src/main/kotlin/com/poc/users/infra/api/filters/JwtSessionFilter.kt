package com.poc.users.infra.api.filters


import com.poc.users.infra.api.security.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtSessionFilter(
    private val jwtUtil : JwtUtil,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        println("FILTER JWT IN HEADER")
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authHeader.substring(7)
        println(token)

        try {
            val claims = jwtUtil.extractAllClaims(token)
            val username = jwtUtil.extractUsername(token)
            val roles = claims.get("roles", List::class.java) as List<*>

            val authorities = roles.map { SimpleGrantedAuthority(it.toString()) }

            val authentication = UsernamePasswordAuthenticationToken(username, null, authorities)
            SecurityContextHolder.getContext().authentication = authentication

        } catch (e: Exception) {
            logger.error("JWT invalide : ${e.message}")
        }

        filterChain.doFilter(request, response)
    }


}
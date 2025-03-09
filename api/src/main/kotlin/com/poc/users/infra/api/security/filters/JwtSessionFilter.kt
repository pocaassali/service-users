package com.poc.users.infra.api.security.filters


import com.poc.users.infra.api.utils.JwtUtil
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
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (authorizationHeader.isNullOrBlank() || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response)
            return
        }

        val jwtToken = authorizationHeader.substring(BEARER_PREFIX.length)

        try {
            val authentication = extractAuthentication(jwtToken)
            SecurityContextHolder.getContext().authentication = authentication
        } catch (e: Exception) {
            handleInvalidTokenResponse(response, e.message)
            return
        }

        filterChain.doFilter(request, response)
    }

    private fun extractAuthentication(jwtToken: String): UsernamePasswordAuthenticationToken {
        val claims = jwtUtil.extractAllClaims(jwtToken)
        val username = jwtUtil.extractUsername(jwtToken)
        val roles = claims.get("roles", List::class.java) as List<*>
        val authorities = roles.map { SimpleGrantedAuthority(it.toString()) }
        return UsernamePasswordAuthenticationToken(username, null, authorities)
    }

    private fun handleInvalidTokenResponse(response: HttpServletResponse, errorMessage: String?) {
        logger.error("JWT invalide : $errorMessage")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"
        val errorResponse = """
        {
            "message": "Invalid JWT token"
        }
    """.trimIndent()
        response.writer.write(errorResponse)
        response.writer.flush()
    }

    companion object {
        private const val BEARER_PREFIX = "Bearer "
    }
}
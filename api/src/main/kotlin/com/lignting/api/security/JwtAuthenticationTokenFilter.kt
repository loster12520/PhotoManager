package com.lignting.api.security

import com.lignting.api.utils.caches.RedisCache
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationTokenFilter(private val redisCache: RedisCache, private val jwtUtils: JwtUtils) :
    OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader("token")
        if (token.isNullOrBlank()) {
            filterChain.doFilter(request, response)
            return
        }
        val userId = try {
            jwtUtils.parseJWT(token).subject
        } catch (e: Exception) {
            throw RuntimeException("token illegal", e)
        }
        val redisKey = "login:$userId"
        val loginUser = redisCache.getCacheObject(redisKey) ?: throw RuntimeException("user not logged in")
        val authenticationToken = UsernamePasswordAuthenticationToken(loginUser, null, null)
        SecurityContextHolder.getContext().authentication = authenticationToken
        filterChain.doFilter(request, response)
    }
}
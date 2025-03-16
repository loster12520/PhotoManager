package com.lignting.api.security

import com.lignting.api.models.CustomUser
import com.lignting.api.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.getUserByUserName(username ?: throw UsernameNotFoundException("username is empty"))
            .orElseThrow { UsernameNotFoundException("username not found") }
        return CustomUser(user, listOf())
    }
}
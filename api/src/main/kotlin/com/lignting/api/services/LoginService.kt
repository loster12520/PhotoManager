package com.lignting.api.services

import com.lignting.api.models.CustomUser
import com.lignting.api.models.CustomUserModel
import com.lignting.api.models.UserDTO
import com.lignting.api.models.toEntity
import com.lignting.api.repositories.UserRepository
import com.lignting.api.security.JwtUtils
import com.lignting.api.utils.caches.RedisCache
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

interface ILoginService {
    fun login(userDTO: UserDTO): Any
    fun register(userDTO: UserDTO): Any
}

@Service
class LoginService(
    private val redisCache: RedisCache,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) : ILoginService {
    override fun login(userDTO: UserDTO): Any {
        val authenticate =
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(userDTO.userName, userDTO.password))
        val user = (authenticate.principal as CustomUser).user
        val token = jwtUtils.createJWT(user)
        redisCache.setCacheObject("login:${user.id}", token)
        return mutableMapOf("token" to token)
    }

    override fun register(userDTO: UserDTO): Any =
        userRepository.getUserByUserName(userDTO.userName)
            .orElseGet {
                userRepository.save(userDTO.also {
                    it.password = passwordEncoder.encode(it.password)
                }.toEntity())
            }
}
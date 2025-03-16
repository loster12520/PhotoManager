package com.lignting.api.services

import com.lignting.api.models.CustomUser
import com.lignting.api.models.UserDTO
import com.lignting.api.security.JwtUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

interface ILoginService {
    fun login(userDTO: UserDTO): MutableMap<String, String>
}

@Service
class LoginService(
    private val authenticationManager: AuthenticationManager, private val jwtUtils: JwtUtils
) : ILoginService {
    override fun login(userDTO: UserDTO): MutableMap<String, String> {
        val authenticate =
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(userDTO.userName, userDTO.password))
        val user = (authenticate.principal as CustomUser).user
        val token = jwtUtils.createJWT(user)
        return mutableMapOf("token" to token)
    }
}
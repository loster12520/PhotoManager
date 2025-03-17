package com.lignting.api.controllers

import com.lignting.api.models.UserDTO
import com.lignting.api.services.LoginService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public")
class LoginController(val loginService: LoginService) {
    @PostMapping("/login")
    fun login(@RequestBody loginUserDTO: UserDTO): Any =
        loginService.login(loginUserDTO)

    @PostMapping("/register")
    fun register(@RequestBody userDTO: UserDTO): Any =
        loginService.register(userDTO)
}
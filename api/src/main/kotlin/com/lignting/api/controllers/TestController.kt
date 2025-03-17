package com.lignting.api.controllers

import com.lignting.api.utils.aspects.ResponseBody
import com.lignting.api.utils.aspects.success
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController {
    @GetMapping("/hello")
    fun hello(): ResponseBody<String> = "Hello World".success().also {
        println(it)
    }
}
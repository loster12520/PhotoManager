package com.lignting.api.security

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.util.DigestUtils

@Component
class CustomPasswordEncoder : PasswordEncoder {
    override fun encode(rawPassword: CharSequence?) =
        String(DigestUtils.md5Digest(rawPassword.toString().byteInputStream()))

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?) =
        encodedPassword == encode(rawPassword)
}
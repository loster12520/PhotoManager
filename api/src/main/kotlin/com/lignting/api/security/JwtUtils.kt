package com.lignting.api.security

import com.lignting.api.models.CustomUserModel
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class JwtUtils {
    private val secretKey = "dansfnkajsnljdnawjdlkanclkasgnlajkdaskndalks".let { Base64.getDecoder().decode(it) }
    private val signatureAlgorithm = SignatureAlgorithm.HS256
    private val secretKeySpec = SecretKeySpec(
        secretKey,
        0, secretKey.size,
        signatureAlgorithm.jcaName
    )
    private val ttl = 60 * 60 * 1000L * 24 * 14

    fun createJWT(user: CustomUserModel, ttlMillis: Long = ttl): String {
        val uuid = UUID.randomUUID().toString().replace("-", "")
        val now = Date()
        val expiryDate = Date(now.time + ttlMillis)
        return Jwts.builder()
            .id(uuid)
            .subject(user.id.toString())
            .issuer("sg")
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secretKeySpec)
            .compact()
    }

    fun parseJWT(jwt: String): Claims =
        Jwts.parser()
            .verifyWith(secretKeySpec)
            .build()
            .parseSignedClaims(jwt)
            .payload
}
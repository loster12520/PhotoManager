package com.lignting.api.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager =
        authenticationConfiguration.authenticationManager

    @Bean
    fun filterChain(
        http: HttpSecurity,
        corsConfigurationSource: CorsConfigurationSource
    ): SecurityFilterChain =
        http
            .csrf { it.disable() }
            .cors(Customizer.withDefaults())
            .cors { it.configurationSource(corsConfigurationSource) }
            .authorizeHttpRequests {
                it.requestMatchers("/public/**").permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .build()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration();
        configuration.allowedHeaders = listOf("*");
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE");
        configuration.allowedOrigins = listOf("*");
        configuration.setMaxAge(3600L);
        val source = UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source
    }
}
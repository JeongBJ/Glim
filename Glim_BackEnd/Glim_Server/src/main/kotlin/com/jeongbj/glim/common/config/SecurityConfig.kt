package com.jeongbj.glim.common.config

import com.jeongbj.glim.security.filter.JwtFilter
import jakarta.servlet.DispatcherType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
class SecurityConfig(
    private val jwtFilter: JwtFilter
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity, corsConfigurationSource: CorsConfigurationSource): SecurityFilterChain =
        http
            .cors { it.configurationSource(corsConfigurationSource) }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.dispatcherTypeMatchers(DispatcherType.ERROR,DispatcherType.ASYNC).permitAll()
                it.requestMatchers("/error").permitAll()
                it.requestMatchers("/privacy").permitAll()
                it.requestMatchers("/child").permitAll()
                it.requestMatchers("/delete").permitAll()
                it.requestMatchers("/auth/reissue").permitAll()
                it.requestMatchers("/login/**").permitAll()
                it.requestMatchers("/.well-known/**").permitAll()
                it.requestMatchers("/share/**").permitAll()
                it.requestMatchers(HttpMethod.GET, "/quote/**").permitAll()
                it.anyRequest().authenticated()
            }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
}
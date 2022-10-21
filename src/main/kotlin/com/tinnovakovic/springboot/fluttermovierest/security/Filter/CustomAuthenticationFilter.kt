package com.tinnovakovic.springboot.fluttermovierest.security.Filter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.HashMap
import kotlin.streams.toList

class CustomAuthenticationFilter(authenticationManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {

    private val log: Logger

    init {
        this.authenticationManager = authenticationManager
        this.log = LoggerFactory.getLogger(CustomAuthenticationFilter::class.java)
    }

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val username: String? = request?.getParameter("username")
        val password: String? = request?.getParameter("password")

        if (username != null && password != null) {
            log.info("Username is $username, password is $password")
            val authenticationToken = UsernamePasswordAuthenticationToken(username, password)
            return this.authenticationManager.authenticate(authenticationToken)
        } else throw AuthenticationServiceException("Username or Password is null")
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        val user: User = authResult?.principal as User
        val algorithm: Algorithm = Algorithm.HMAC256("secret".encodeToByteArray())
        val accessToken = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 100 * 60 * 1000)) //10 minutes
            .withIssuer(request?.requestURL.toString())
            .withClaim("roles", user.authorities.stream().map { it.authority }.toList())
            .sign(algorithm)

        val refreshToken = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 300 * 60 * 1000)) //30 minutes
            .withIssuer(request?.requestURL.toString())
            .sign(algorithm)

        val tokens: MutableMap<String, String> = HashMap()
        tokens["access_token"] = accessToken
        tokens["refresh_token"] = refreshToken
        response?.contentType = APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(response?.outputStream, tokens)
    }
}

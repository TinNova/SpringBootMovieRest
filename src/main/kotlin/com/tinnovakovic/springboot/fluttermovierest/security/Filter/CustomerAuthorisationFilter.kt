package com.tinnovakovic.springboot.fluttermovierest.security.Filter

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.web.filter.OncePerRequestFilter
import java.util.Arrays.stream
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomerAuthorisationFilter() : OncePerRequestFilter() {

    val log: Logger = LoggerFactory.getLogger(CustomerAuthorisationFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        if (request.servletPath.equals("/api/login") || request.servletPath.equals("/api/users/token/refresh")) {
            filterChain.doFilter(request, response)
        } else {
            val authorisationHeader = request.getHeader(AUTHORIZATION)
            if (authorisationHeader != null && authorisationHeader.startsWith("Bearer ")) {
                try {
                    val token = authorisationHeader.substring("Bearer ".length)
                    val algorithm = Algorithm.HMAC256("secret")
                    val verifier: JWTVerifier = JWT.require(algorithm).build()
                    val decodeJWT: DecodedJWT = verifier.verify(token)
                    val username = decodeJWT.subject
                    val roles =
                        decodeJWT.getClaim("roles").asArray(String.Companion::class.java)
                    val authorities: MutableCollection<SimpleGrantedAuthority> = mutableListOf()
                    stream(roles).forEach {
                        authorities.add(SimpleGrantedAuthority(it.toString()))
                    }
                    val authenticationToken = UsernamePasswordAuthenticationToken(username, null, authorities)
                    SecurityContextHolder.getContext().authentication = authenticationToken
                    filterChain.doFilter(request, response)

                } catch (e: Exception) {
                    log.error("Error logging in: ${e.message}")
                    response.setHeader("error", e.message)
                    response.status = FORBIDDEN.value()
                    val error: MutableMap<String, String> = HashMap()
                    error["error_message"] = e.message ?: "null"
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    ObjectMapper().writeValue(response.outputStream, error)


                }
            } else {
                filterChain.doFilter(request, response)
            }
        }
    }
}
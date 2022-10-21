package com.tinnovakovic.springboot.fluttermovierest.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.fasterxml.jackson.databind.ObjectMapper
import com.tinnovakovic.springboot.fluttermovierest.model.Role
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestAppUser
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestSaveActor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestSaveMovie
import com.tinnovakovic.springboot.fluttermovierest.service.UserService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.util.*
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.NoSuchElementException
import kotlin.collections.HashMap
import kotlin.streams.toList

@RestController
@RequestMapping("/api/users")
class UserController(private val service: UserService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping("/")
    fun getUsers(): List<RestAppUser> = service.getRestAppUsers()

    // This pathVariable needs to be replaced with a username password authentication
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): RestAppUser = service.getRestAppUser(id)

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveMovieToUser(@PathVariable userId: Int, @RequestBody restSaveMovie: RestSaveMovie): Boolean =
        service.saveMovieToUser(userId, restSaveMovie)

    @PatchMapping("/{userId}/saveactor")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveActorToUser(@PathVariable userId: Int, @RequestBody restSaveActor: RestSaveActor): Boolean =
        service.saveActorToUser(userId, restSaveActor)

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveUser(@RequestBody restAppUser: RestAppUser): RestAppUser = service.saveUser(restAppUser)

    @PostMapping("/role")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveRole(@RequestBody role: Role): Role = service.saveRole(role)

    // Maybe return boolean just so we know if it was successful or not
    @PostMapping("/user/{id}/role/{roleName}")
    @ResponseStatus(HttpStatus.CREATED)
    fun addRoleToUser(@RequestBody form: RoleToUserForm) = service.addRoleToUser(form.userEmail, form.roleName)

    @GetMapping("/token/refresh")
    @ResponseStatus(HttpStatus.CREATED)
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) {
        val authorisationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authorisationHeader != null && authorisationHeader.startsWith("Bearer ")) {
            try {
                val refreshToken = authorisationHeader.substring("Bearer ".length)
                val algorithm = Algorithm.HMAC256("secret")
                val verifier: JWTVerifier = JWT.require(algorithm).build()
                val decodeJWT: DecodedJWT = verifier.verify(refreshToken)
                val username = decodeJWT.subject

                val user = service.getAppUser(username)

                val accessToken = JWT.create()
                    .withSubject(user.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + 100 * 60 * 10000)) //100 minutes
                    .withIssuer(request?.requestURL.toString())
                    .withClaim("roles", user.roles.stream().map { it.name }.toList())
                    .sign(algorithm)

                val tokens: MutableMap<String, String> = HashMap()
                tokens["access_token"] = accessToken
                tokens["refresh_token"] = refreshToken
                response?.contentType = MediaType.APPLICATION_JSON_VALUE
                ObjectMapper().writeValue(response?.outputStream, tokens)

            } catch (e: Exception) {
                response.setHeader("error", e.message)
                response.status = HttpStatus.FORBIDDEN.value()
                val error: MutableMap<String, String> = HashMap()
                error["error_message"] = e.message ?: "null"
                response.contentType = MediaType.APPLICATION_JSON_VALUE
                ObjectMapper().writeValue(response.outputStream, error)


            }
        } else {
            java.lang.RuntimeException("Refresh token missing")
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable id: Int): Unit = service.deleteUser(id)
}

data class RoleToUserForm(
    val userEmail: String,
    val roleName: String
)
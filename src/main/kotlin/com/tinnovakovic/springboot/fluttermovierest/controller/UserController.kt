package com.tinnovakovic.springboot.fluttermovierest.controller

import com.tinnovakovic.springboot.fluttermovierest.model.Role
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestAppUser
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestSaveActor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestSaveMovie
import com.tinnovakovic.springboot.fluttermovierest.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException

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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable id: Int): Unit = service.deleteUser(id)
}

data class RoleToUserForm(
    val userEmail: String,
    val roleName: String
)
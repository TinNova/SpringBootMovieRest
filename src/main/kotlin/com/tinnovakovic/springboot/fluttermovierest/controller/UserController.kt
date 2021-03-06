package com.tinnovakovic.springboot.fluttermovierest.controller

import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestAppUser
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovie
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
    fun getUsers(): List<RestAppUser> = service.getUsers()

    // This pathVariable needs to be replaced with a username password authentication
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): RestAppUser = service.getUser(id)

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveMovie(@PathVariable userId: Int, @RequestBody restMovie: RestMovie): RestMovie = service.saveMovie(userId, restMovie)

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun addUser(@RequestBody restAppUser: RestAppUser): RestAppUser = service.createUser(restAppUser)

//    @PatchMapping("/")
//    fun updateUser(@RequestBody restAppUser: RestAppUser): RestAppUser = service.updateUser(restAppUser)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable id: Int): Unit = service.deleteUser(id)
}
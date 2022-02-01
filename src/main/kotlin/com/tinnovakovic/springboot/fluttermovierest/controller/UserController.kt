package com.tinnovakovic.springboot.fluttermovierest.controller

import com.tinnovakovic.springboot.fluttermovierest.model.AppUser
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
    fun getUsers(): List<AppUser> = service.getUsers()

    // This pathVariable needs to be replaced with a username password authentication
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): AppUser = service.getUser(id)

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun addUser(@RequestBody appUser: AppUser): AppUser = service.createUser(appUser)

    @PatchMapping("/")
    fun updateUser(@RequestBody appUser: AppUser): AppUser = service.updateUser(appUser)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable id: Int): Unit = service.deleteUser(id)
}
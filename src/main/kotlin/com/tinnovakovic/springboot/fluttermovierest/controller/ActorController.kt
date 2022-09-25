package com.tinnovakovic.springboot.fluttermovierest.controller

import com.tinnovakovic.springboot.fluttermovierest.rest_models.CreateActor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestActor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestActorDetail
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovieDetail
import com.tinnovakovic.springboot.fluttermovierest.service.ActorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("/api/actors")
class ActorController(private val service: ActorService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun createActor(@RequestBody createActor: CreateActor): RestActor = service.createActor(createActor)

    @GetMapping("/{actorId}")
    fun getActorDetail(@PathVariable actorId: Int): RestActorDetail =
        service.getRestActorDetail(actorId) // do we want to return RestActor or RestActorDetail?

    @GetMapping("/")
    fun getActors(@RequestBody actorIds: List<Int>): List<RestActor> =
        service.getRestActors(actorIds) // do we want to return RestActor or RestActorDetail?

    @GetMapping("/user/{id}")
    fun getFavouriteActors(@PathVariable id: Int): List<RestActorDetail> =
        service.getFavouriteActors(id) // do we want to return RestActor or RestActorDetail?
}

package com.tinnovakovic.springboot.fluttermovierest.controller

import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovie
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovieDetail
import com.tinnovakovic.springboot.fluttermovierest.service.MovieService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import kotlin.NoSuchElementException

@RestController
@RequestMapping("/api/movies")
class MovieController(private val service: MovieService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping("/")
    fun getMovies(): List<RestMovie> = service.getRestMovies()

    @GetMapping("/{id}/user/{userId}")
    fun getMovie(@PathVariable id: Int, @PathVariable userId: Int): RestMovieDetail = service.getRestMovie(id, userId)

    // Put this in a separate Controller called FavouriteController?
    @GetMapping("/user/{id}")
    fun getFavouriteMovies(@PathVariable id: Int): List<RestMovieDetail> = service.getFavouriteMovies(id)

    @GetMapping()
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun createMovie(@RequestBody restMovieDetail: RestMovieDetail): RestMovie = service.createMovie(restMovieDetail)

    @PatchMapping("/")
    fun updateMovie(@RequestBody restMovieDetail: RestMovieDetail): RestMovieDetail =
        service.updateMovie(restMovieDetail)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMovie(@PathVariable id: Int): Unit = service.deleteMovie(id)
}
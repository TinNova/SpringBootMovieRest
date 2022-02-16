package com.tinnovakovic.springboot.fluttermovierest.controller

import com.tinnovakovic.springboot.fluttermovierest.model.Movie
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
    fun getMovies(): List<RestMovie> = service.getMovies()

    @GetMapping("/{id}")
    fun getMovie(@PathVariable id: Int): RestMovieDetail = service.getMovie(id)

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun addMovie(@RequestBody restMovieDetail: RestMovieDetail): RestMovie = service.createMovie(restMovieDetail)

    @PatchMapping("/")
    fun updateMovie(@RequestBody restMovieDetail: RestMovieDetail): RestMovieDetail = service.updateMovie(restMovieDetail)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMovie(@PathVariable id: Int): Unit = service.deleteMovie(id)
}
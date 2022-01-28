package com.tinnovakovic.springboot.fluttermovierest.controller

import com.tinnovakovic.springboot.fluttermovierest.model.Movie
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
    fun getMovies(): List<Movie> = service.getMovies()

    @GetMapping("/{id}")
    fun getMovie(@PathVariable id: Int): Movie = service.getMovie(id)

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun addMovie(@RequestBody movie: Movie): Movie = service.createMovie(movie)

    @PatchMapping("/")
    fun updateMovie(@RequestBody movie: Movie): Movie = service.updateMovie(movie)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMovie(@PathVariable id: Int): Unit = service.deleteMovie(id)
}
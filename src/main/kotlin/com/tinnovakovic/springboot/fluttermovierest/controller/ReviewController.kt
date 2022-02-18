package com.tinnovakovic.springboot.fluttermovierest.controller

import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestReview
import com.tinnovakovic.springboot.fluttermovierest.service.ReviewService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("/api/reviews")
class ReviewController(private val service: ReviewService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @PostMapping("/userdetail/{userDetailId}/moviedetail/{movieDetailId}")
    fun addReview(
        @PathVariable("userDetailId") userDetailId: Int,
        @PathVariable("movieDetailId") movieDetailId: Int,
        @RequestBody restReview: RestReview
    ): RestReview = service.createReview(userDetailId, movieDetailId, restReview)

    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable reviewId: Int): Unit = service.deleteReview(reviewId)

    @PatchMapping("/")
    fun updateUser(@RequestBody restReview: RestReview): RestReview = service.updateReview(restReview)

}
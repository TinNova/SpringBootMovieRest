package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.repo.BulkRepo
import org.springframework.stereotype.Service

@Service
class BulkDownloadServiceImpl(
    private val repo: BulkRepo,
    private val service: MovieService
) : BulkDownloadService {
    override fun bulkDownloadMovies() {
        val restMovieDetails = repo.bulkDownloadMovies()
        service.createMovies(restMovieDetails)
        //repo.bulkDownloadActors()
        //repo.bulkDownloadReviews()
        // this should trigger
        // Movies
        // Actors
        // Reviews
        // NOT Users
    }
}
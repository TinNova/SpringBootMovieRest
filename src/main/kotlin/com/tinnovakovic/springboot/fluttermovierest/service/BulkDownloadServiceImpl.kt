package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.repo.BulkRepo
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovieDetail
import org.springframework.stereotype.Service

@Service
class BulkDownloadServiceImpl(
    private val repo: BulkRepo,
    private val movieService: MovieService,
    private val actorService: ActorService
) : BulkDownloadService {
    override fun bulkDownloadData() {
        bulkDownloadMovies()
    }

    private fun bulkDownloadMovies() {
        val restMovieDetails = repo.bulkDownloadMovies()
        movieService.bulkSaveMovies(restMovieDetails)
        bulkDownloadActors(restMovieDetails)
    }

    private fun bulkDownloadActors(restMovieDetails: List<RestMovieDetail>) {
        val createActors = repo.bulkDownloadActors(restMovieDetails)
        actorService.bulkSaveActors(createActors)
    }
}
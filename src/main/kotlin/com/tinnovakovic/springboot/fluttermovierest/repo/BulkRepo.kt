package com.tinnovakovic.springboot.fluttermovierest.repo

import com.tinnovakovic.springboot.fluttermovierest.rest_models.CreateActor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovieDetail

interface BulkRepo {

    fun bulkDownloadMovies(): List<RestMovieDetail>
    fun bulkDownloadActors(restMovieDetails: List<RestMovieDetail>): List<CreateActor>
    fun bulkDownloadReviews()
}
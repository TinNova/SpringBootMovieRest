package com.tinnovakovic.springboot.fluttermovierest.repo

import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovieDetail

interface BulkRepo {

    fun bulkDownloadMovies(): List<RestMovieDetail>
    fun bulkDownloadActors()
    fun bulkDownloadReviews()
}
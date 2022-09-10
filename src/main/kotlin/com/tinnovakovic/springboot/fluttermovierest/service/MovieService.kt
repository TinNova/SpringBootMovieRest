package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovie
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovieDetail

interface MovieService {

    fun getRestMovies(): List<RestMovie>
    fun getRestMovie(id: Int): RestMovieDetail
    fun createMovie(restMovieDetail: RestMovieDetail): RestMovie
    fun bulkSaveMovies(restMovieDetails: List<RestMovieDetail>)
    fun updateMovie(restMovieDetail: RestMovieDetail): RestMovieDetail
    fun deleteMovie(id: Int): Unit
}
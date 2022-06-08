package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovie
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovieDetail

interface MovieService {

    fun getMovies(): List<RestMovie>
    fun getMovie(id: Int): RestMovieDetail
    fun createMovie(restMovieDetail: RestMovieDetail): RestMovie
    fun bulkSaveMovies(restMovieDetails: List<RestMovieDetail>)
    fun updateMovie(restMovieDetail: RestMovieDetail): RestMovieDetail
    fun deleteMovie(id: Int): Unit
}
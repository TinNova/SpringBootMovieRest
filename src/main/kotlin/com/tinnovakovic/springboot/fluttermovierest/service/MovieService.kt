package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import com.tinnovakovic.springboot.fluttermovierest.model.MovieDetail
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovie
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovieDetail

interface MovieService {

    fun getRestMovies(): List<RestMovie>
    fun getMovies(): List<Movie>
    fun getRestMovie(id: Int): RestMovieDetail
    fun createMovie(restMovieDetail: RestMovieDetail): RestMovie
    fun bulkSaveMovies(restMovieDetails: List<RestMovieDetail>)
    fun updateMovie(restMovieDetail: RestMovieDetail): RestMovieDetail
    fun updateMovie(movie: Movie): Movie
    fun deleteMovie(id: Int): Unit
}
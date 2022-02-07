package com.tinnovakovic.springboot.fluttermovierest.datasource

import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovie

interface MovieDataSource {

    fun retrieveMovies(): List<RestMovie>
    fun retrieveMovie(id: Int): RestMovie
    fun createMovie(movie: Movie): Movie
    fun updateMovie(movie: Movie): Movie
    fun deleteMovie(id: Int)
}
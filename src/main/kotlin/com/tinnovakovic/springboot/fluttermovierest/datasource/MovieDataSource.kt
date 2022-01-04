package com.tinnovakovic.springboot.fluttermovierest.datasource

import com.tinnovakovic.springboot.fluttermovierest.model.Movie

interface MovieDataSource {

    fun retrieveMovies(): List<Movie>
    fun retrieveMovie(id: Int): Movie
    fun createMovie(movie: Movie): Movie
    fun updateMovie(movie: Movie): Movie
    fun deleteMovie(id: Int)
}
package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovie
import org.springframework.stereotype.Service

@Service
interface MovieService {

    fun getMovies(): List<RestMovie>
    fun getMovie(id: Int): RestMovie
    fun createMovie(movie: Movie): Movie
    fun updateMovie(movie: Movie): Movie
    fun deleteMovie(id: Int): Unit
}
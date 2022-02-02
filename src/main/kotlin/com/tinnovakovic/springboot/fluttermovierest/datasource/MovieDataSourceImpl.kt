package com.tinnovakovic.springboot.fluttermovierest.datasource

import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class MovieDataSourceImpl(private val movieRepo: MovieRepo) : MovieDataSource {

    override fun retrieveMovies(): List<Movie> {
        return movieRepo.findAll()
    }

    override fun retrieveMovie(id: Int): Movie {
        movieRepo.findById(id).let {
            return if (it.isPresent) {
                it.get()
            } else {
                throw NoSuchElementException("Could not find a movie with an 'id' of $id.")
            }
        }
    }

    override fun createMovie(movie: Movie): Movie {
        return if (movieRepo.findById(movie.id).isEmpty) {
            movie.movieDetail = movie.movieDetail.copy(movieId = movie.movieId, posterPath = movie.posterPath)// = movieDetail
            movieRepo.save(movie)
        } else {
            throw IllegalArgumentException("A movie with the 'id' ${movie.id} already exists")
        }
    }

    override fun updateMovie(movie: Movie): Movie {
        return if (movieRepo.findById(movie.id).isPresent) {
            movieRepo.save(movie)
        } else {
            throw NoSuchElementException("Could not find a movie with an 'id' of ${movie.id}.")
        }
    }

    override fun deleteMovie(id: Int) {
        if (movieRepo.findById(id).isPresent) {
            movieRepo.deleteById(id)
        } else {
            throw NoSuchElementException("Could not find a movie with an 'id' of ${id}.")
        }
    }
}
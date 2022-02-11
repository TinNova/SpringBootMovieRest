package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.repo.MovieRepo
import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovie
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class MovieServiceImpl(private val movieRepo: MovieRepo): MovieService {

    override fun getMovies(): List<RestMovie> {
        return movieRepo.findAll().map {
            RestMovie(id = it.id, movieId = it.movieId, posterPath = it.posterPath)
        }
    }

    override fun getMovie(id: Int): RestMovie {
        movieRepo.findById(id).let {
            return if (it.isPresent) {
                RestMovie(id = it.get().id, movieId = it.get().movieId, posterPath = it.get().posterPath)
            } else {
                throw NoSuchElementException("Could not find a movie with an 'id' of $id.")
            }
        }
    }

    /**
     * Movies can only be created by an Admin, not a user, so it's ok to use the SQL Movie model because the
     * admin needs to populate the MovieDetail at the same time that they create a Movie
     */
    override fun createMovie(movie: Movie): Movie {
        return if (movieRepo.findById(movie.id).isEmpty) {
            movie.movieDetail =
                movie.movieDetail.copy(movieId = movie.movieId, posterPath = movie.posterPath)// = movieDetail
            movieRepo.save(movie)
        } else {
            throw IllegalArgumentException("A movie with the 'id' ${movie.id} already exists")
        }
    }

    /**
     * Movies can only be updated by an Admin, not a user, so using the SQL Movie model is fine
     */
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
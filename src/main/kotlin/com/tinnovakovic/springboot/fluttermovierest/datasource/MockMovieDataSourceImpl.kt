package com.tinnovakovic.springboot.fluttermovierest.datasource

import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class MockMovieDataSourceImpl : MovieDataSource {

    val movies = mutableListOf(
        Movie(1, "1001", "posterPath"),
        Movie(2, "1002", "posterPath"),
        Movie(3, "1003", "posterPath")
    )

    override fun retrieveMovies(): List<Movie> {
        return movies
    }

    override fun retrieveMovie(id: Int): Movie {
        return movies.firstOrNull { it.id == id }
            ?: throw NoSuchElementException("Could not find a movie with an 'id' of $id.")
    }

    override fun createMovie(movie: Movie): Movie {
        if (movies.any { it.id == movie.id }) {
            throw IllegalArgumentException("A movie with the 'id' ${movie.id} already exists")
        }
        movies.add(movie)
        return movie
    }

    override fun updateMovie(movie: Movie): Movie {
        val currentMovie = movies.firstOrNull { it.id == movie.id }
            ?: throw NoSuchElementException("Could not find a movie with an 'id' of ${movie.id}.")

        val currentMovieIndex = movies.indexOf(currentMovie)
        movies.set(currentMovieIndex, movie)
        return movie
    }

    override fun deleteMovie(id: Int) {
        val currentMovie = movies.firstOrNull { it.id == id }
            ?: throw NoSuchElementException("Could not find a movie with an 'id' of ${id}.")

        movies.remove(currentMovie)
    }
}
package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.repo.MovieRepo
import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import com.tinnovakovic.springboot.fluttermovierest.repo.MovieDetailRepo
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovie
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovieDetail
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class MovieServiceImpl(
    private val movieRepo: MovieRepo,
    private val movieDetailRepo: MovieDetailRepo
) : MovieService {

    override fun getMovies(): List<RestMovie> {
        return movieRepo.findAll().map {
            RestMovie(id = it.id, mDbId = it.mDbId, posterPath = it.posterPath)
        }
    }

    override fun getMovie(id: Int): RestMovieDetail {
        movieDetailRepo.findById(id).let {
            return if (it.isPresent) {
                RestMovieDetail(
                    id = it.get().id,
                    mDbId = it.get().mDbId,
                    title = it.get().title,
                    overview = it.get().overview,
                    posterPath = it.get().posterPath,
                    backdropPath = it.get().backdropPath,
                    directors = it.get().directors,
                    popularity = it.get().popularity,
                    releaseDate = it.get().releaseDate,
                    revenue = it.get().revenue,
                    runtime = it.get().runtime,
                    tagline = it.get().tagline,
                    voteAverage = it.get().voteAverage,
                    voteCount = it.get().voteCount,
                    isFavourite = it.get().isFavourite,
                    appUsers = it.get().appUsers.map { it.id }
                )
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
                movie.movieDetail.copy(mDbId = movie.mDbId, posterPath = movie.posterPath)// = movieDetail
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
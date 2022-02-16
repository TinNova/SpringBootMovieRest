package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.repo.MovieRepo
import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import com.tinnovakovic.springboot.fluttermovierest.model.MovieDetail
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
                    isFavourite = it.get().isFavourite
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
    override fun createMovie(restMovieDetail: RestMovieDetail): RestMovie {
        return if (movieRepo.findById(restMovieDetail.id).isEmpty) { // doesn't make sense to use id as an id is created by SQL, so check against MdbId
            val movie = movieRepo.save(
                Movie(
                    id = -1,
                    mDbId = restMovieDetail.mDbId,
                    posterPath = restMovieDetail.posterPath,
                    movieDetail = MovieDetail(
                        id = restMovieDetail.id,
                        mDbId = restMovieDetail.mDbId,
                        title = restMovieDetail.title,
                        overview = restMovieDetail.overview,
                        posterPath = restMovieDetail.posterPath,
                        backdropPath = restMovieDetail.backdropPath,
                        directors = restMovieDetail.directors,
                        popularity = restMovieDetail.popularity,
                        releaseDate = restMovieDetail.releaseDate,
                        revenue = restMovieDetail.revenue,
                        runtime = restMovieDetail.runtime,
                        tagline = restMovieDetail.tagline,
                        voteAverage = restMovieDetail.voteAverage,
                        voteCount = restMovieDetail.voteCount,
                        isFavourite = restMovieDetail.isFavourite
                    ),
                    appUsers = emptySet()
                )
            )
            RestMovie(id = movie.id, mDbId = movie.mDbId, posterPath = movie.posterPath)
        } else {
            throw IllegalArgumentException("A movie with the 'id' ${restMovieDetail.id} already exists")
        }
    }

    override fun updateMovie(restMovieDetail: RestMovieDetail): RestMovieDetail {
        movieRepo.findById(restMovieDetail.id).let {
            return if (it.isPresent) {
                movieRepo.save(
                    it.get().copy(
                        id = restMovieDetail.id,
                        mDbId = restMovieDetail.mDbId,
                        posterPath = restMovieDetail.posterPath,
                        movieDetail = MovieDetail(
                            id = restMovieDetail.id,
                            mDbId = restMovieDetail.mDbId,
                            title = restMovieDetail.title,
                            overview = restMovieDetail.overview,
                            posterPath = restMovieDetail.posterPath,
                            backdropPath = restMovieDetail.backdropPath,
                            directors = restMovieDetail.directors,
                            popularity = restMovieDetail.popularity,
                            releaseDate = restMovieDetail.releaseDate,
                            revenue = restMovieDetail.revenue,
                            runtime = restMovieDetail.runtime,
                            tagline = restMovieDetail.tagline,
                            voteAverage = restMovieDetail.voteAverage,
                            voteCount = restMovieDetail.voteCount,
                            isFavourite = restMovieDetail.isFavourite
                        ),
                        appUsers = it.get().appUsers
                    )
                )
                restMovieDetail
            } else {
                throw NoSuchElementException("Could not find a movie with an 'id' of ${restMovieDetail.id}.")
            }
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
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
                    isFavourite = it.get().isFavourite,
                    reviews = it.get().reviews.map { review -> review.id }.toSet(), //do we want to return ids? Why not return what's required for the screen?
                    actors = it.get().actors.map { actor -> actor.id }.toSet() //do we want to return ids? Why not return what's required for the screen?
                )
            } else {
                throw NoSuchElementException("Could not find a movie with an 'id' of $id.")
            }
        }
    }

    override fun createMovie(restMovieDetail: RestMovieDetail): RestMovie {
        return if (movieRepo.findByMdbId(restMovieDetail.mDbId).isEmpty) {
            val movie = movieRepo.save(
                Movie(
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
                        isFavourite = restMovieDetail.isFavourite,
                        reviews = emptySet(),
                        actors = emptySet()
                    ),
                    appUsers = emptySet()
                )
            )
            RestMovie(id = movie.id, mDbId = movie.mDbId, posterPath = movie.posterPath)
        } else {
            throw IllegalArgumentException("A movie with a 'mDbId' of ${restMovieDetail.mDbId} already exists")
        }
    }

    override fun createMovies(restMovieDetails: List<RestMovieDetail>) {
        restMovieDetails.forEach { restMovieDetail ->
            if (movieRepo.findByMdbId(restMovieDetail.mDbId).isEmpty) {
                movieRepo.save(
                    Movie(
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
                            isFavourite = restMovieDetail.isFavourite,
                            reviews = emptySet(),
                            actors = emptySet()
                        ),
                        appUsers = emptySet()
                    )
                )
            } else {
                throw IllegalArgumentException("During Bulk Save Operation, a movie with a 'mDbId' of ${restMovieDetail.mDbId} already exists")
            }
        }
    }

    // We don't want to updateMovies, at least we don't want this endpoint to be exposed to the Flutter app
    // This is something only admin should be allowed to do
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
                            isFavourite = restMovieDetail.isFavourite,
                            reviews = it.get().movieDetail.reviews,
                            actors = it.get().movieDetail.actors
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
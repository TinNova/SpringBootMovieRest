package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.repo.MovieRepo
import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import com.tinnovakovic.springboot.fluttermovierest.model.MovieDetail
import com.tinnovakovic.springboot.fluttermovierest.repo.MovieDetailRepo
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestAppUser
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovie
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovieDetail
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class MovieServiceImpl(
    private val movieRepo: MovieRepo,
    private val movieDetailRepo: MovieDetailRepo,
    private val userService: UserService
) : MovieService {

    override fun getRestMovies(): List<RestMovie> {
        return getMovies().map {
            RestMovie(id = it.id, mDbId = it.mDbId, posterPath = it.posterPath)
        }
    }

    override fun getMovies(): List<Movie> {
        return movieRepo.findAll()
    }

    override fun getRestMovie(id: Int): RestMovieDetail {
        return getMovie(id).let { movieDetail ->
            RestMovieDetail(
                id = movieDetail.id,
                mDbId = movieDetail.mDbId,
                title = movieDetail.title,
                overview = movieDetail.overview,
                posterPath = movieDetail.posterPath,
                backdropPath = movieDetail.backdropPath,
                directors = movieDetail.directors,
                popularity = movieDetail.popularity,
                releaseDate = movieDetail.releaseDate,
                revenue = movieDetail.revenue,
                runtime = movieDetail.runtime,
                tagline = movieDetail.tagline,
                voteAverage = movieDetail.voteAverage,
                voteCount = movieDetail.voteCount,
                isFavourite = movieDetail.isFavourite,
                reviews = movieDetail.reviews.map { review -> review.id }
                    .toSet(), //do we want to return ids? Why not return what's required for the screen?
                actors = movieDetail.actors.map { actor -> actor.id }
                    .toSet() //do we want to return ids? Why not return what's required for the screen?
            )
        }
    }

    private fun getMovie(id: Int): MovieDetail {
        movieDetailRepo.findById(id).let {
            return if (it.isPresent) it.get()
            else throw NoSuchElementException("Could not find a movie with an 'id' of $id.")
        }
    }

    //this should be in a helper class instead of in the ServiceImpl
    // UserService Should not be in this clas
    override fun getFavouriteMovies(userId: Int): List<RestMovieDetail> {
        val user: RestAppUser = userService.getRestAppUser(userId)

        return getMoviesById(user.movies).map { movieDetail ->
            RestMovieDetail(
                id = movieDetail.id,
                mDbId = movieDetail.mDbId,
                title = movieDetail.title,
                overview = movieDetail.overview,
                posterPath = movieDetail.posterPath,
                backdropPath = movieDetail.backdropPath,
                directors = movieDetail.directors,
                popularity = movieDetail.popularity,
                releaseDate = movieDetail.releaseDate,
                revenue = movieDetail.revenue,
                runtime = movieDetail.runtime,
                tagline = movieDetail.tagline,
                voteAverage = movieDetail.voteAverage,
                voteCount = movieDetail.voteCount,
                isFavourite = movieDetail.isFavourite,
                reviews = movieDetail.reviews.map { review -> review.id }
                    .toSet(), //do we want to return ids? Why not return what's required for the screen?
                actors = movieDetail.actors.map { actor -> actor.id }
                    .toSet() //do we want to return ids? Why not return what's required for the screen?
            )
        }
    }

    private fun getMoviesById(ids: Set<Int>): List<MovieDetail> = movieDetailRepo.findAllById(ids)

    override fun createMovie(restMovieDetail: RestMovieDetail): RestMovie {
        val movie = Movie(
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
                actors = mutableSetOf()
            ),
            appUsers = emptySet()
        )

        val savedMovie = createMovie(movie)
        return RestMovie(id = savedMovie.id, mDbId = savedMovie.mDbId, posterPath = savedMovie.posterPath)
    }


    private fun createMovie(movie: Movie): Movie {
        if (movieRepo.findByMdbId(movie.mDbId).isEmpty) {
            return movieRepo.save(movie)
        } else {
            throw IllegalArgumentException("A movie with a 'mDbId' of ${movie.mDbId} already exists")
        }
    }

    override fun bulkSaveMovies(restMovieDetails: List<RestMovieDetail>) {
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
                            actors = mutableSetOf()
                        ),
                        appUsers = emptySet()
                    )
                )
            } else {
//                throw IllegalArgumentException("During Bulk Save Operation, a movie with a 'mDbId' of ${restMovieDetail.mDbId} already exists")
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

    override fun updateMovie(movie: Movie): Movie {
        movieRepo.findById(movie.id).let {
            return if (it.isPresent) {
                movieRepo.save(
                    movie
                )
            } else {
                throw NoSuchElementException("Could not find a movie with an 'id' of ${movie.id}.")
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
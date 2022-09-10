package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.mdb_models.MdbCast
import com.tinnovakovic.springboot.fluttermovierest.mdb_models.MdbCredit
import com.tinnovakovic.springboot.fluttermovierest.model.Actor
import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import com.tinnovakovic.springboot.fluttermovierest.repo.BulkRepo
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovieDetail
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class BulkDownloadServiceImpl(
    private val repo: BulkRepo, private val movieService: MovieService, private val actorService: ActorService
) : BulkDownloadService {

    @Value("\${api_key}")
    private val apiKey: String? = null

    override fun bulkDownloadData() {
        bulkDownloadMovies()
    }

    private fun bulkDownloadMovies() {
        val restMovieDetails = repo.bulkDownloadMovies()
        movieService.bulkSaveMovies(restMovieDetails)
        bulkDownloadActors(restMovieDetails)
    }

    private fun bulkDownloadActors(restMovieDetails: List<RestMovieDetail>) {
        val createActors = repo.bulkDownloadActors(restMovieDetails)
        actorService.bulkSaveActors(createActors)
        combineMoviesAndActors()
    }

    private fun combineMoviesAndActors() {
        val movies: List<Movie> = movieService.getMovies()
        movies.forEach { movie ->

            val mdbCast: List<MdbCast> = RestTemplate().getForObject(
                "https://api.themoviedb.org/3/movie/${movie.mDbId}/credits?api_key=${apiKey}&language=en-US",
                MdbCredit::class.java
            )!!.cast

            val actors: List<Actor> = actorService.getActors(mdbCast.map { it.cast_id })
            movie.movieDetail.actors.addAll(actors)
            movieService.updateMovie(movie)
        }
    }
}
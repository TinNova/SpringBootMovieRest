package com.tinnovakovic.springboot.fluttermovierest.mapper

import com.tinnovakovic.springboot.fluttermovierest.mdb_models.MdbMovieDetail
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovieDetail
import org.springframework.stereotype.Component

@Component
class MdbMapper {

    fun mapToRestMovies(movieDetails: List<MdbMovieDetail>): List<RestMovieDetail> {

        return movieDetails.map {
            RestMovieDetail(
                id = it.id,
                mDbId = it.imdb_id ?: "",
                title = it.title,
                overview = it.overview,
                posterPath = it.poster_path,
                backdropPath = it.backdrop_path,
                directors = "", // figure this out later,
                popularity = it.popularity,
                releaseDate = it.release_date,
                revenue = it.revenue.toDouble(),
                runtime = it.runtime.toString(),
                tagline = it.tagline,
                voteAverage = it.vote_average,
                voteCount = it.vote_count,
                isFavourite = false,
                reviews = emptySet(),
                actors = emptySet() //is this the correct thing to do?
            )
        }

    }
}
package com.tinnovakovic.springboot.fluttermovierest.mapper

import com.tinnovakovic.springboot.fluttermovierest.mdb_models.MdbActorDetail
import com.tinnovakovic.springboot.fluttermovierest.mdb_models.MdbMovieDetail
import com.tinnovakovic.springboot.fluttermovierest.rest_models.CreateActor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovieDetail
import org.springframework.stereotype.Component

@Component
class MdbMapper {

    fun mapToRestMovieDetails(movieDetails: List<MdbMovieDetail>): List<RestMovieDetail> {

        return movieDetails.map {
            RestMovieDetail(
                id = 0,
                mDbId = it.id.toString(),
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

    fun mapToCreateActors(mdbCast: MutableList<MdbActorDetail>): List<CreateActor> {

        return mdbCast.map {
            CreateActor(
                id = 0,
                actorMdbId = it.id.toString(),
                name = it.name,
                profilePath = it.profile_path ?: "",
                biography = it.biography, //missing from MdbCast
                movieDetails = emptySet()
            )
        }
    }

}
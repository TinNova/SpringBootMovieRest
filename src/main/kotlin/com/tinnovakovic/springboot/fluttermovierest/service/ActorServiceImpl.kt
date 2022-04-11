package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.model.Actor
import com.tinnovakovic.springboot.fluttermovierest.repo.ActorRepo
import com.tinnovakovic.springboot.fluttermovierest.repo.MovieDetailRepo
import com.tinnovakovic.springboot.fluttermovierest.rest_models.CreateActor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestActor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestActorDetail
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovieCredit
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class ActorServiceImpl(
    private val actorRepo: ActorRepo,
    private val movieDetailRepo: MovieDetailRepo
) : ActorService {

    override fun createActor(createActor: CreateActor): RestActor {
        return if (actorRepo.findByActorMdbId(createActor.actorMdbId).isEmpty) {

            // Add the correct checks here to make sure we're not saving an actor that already exists ect...
            val actor = actorRepo.save(
                Actor(
                    id = createActor.id,
                    actorMdbId = createActor.actorMdbId,
                    name = createActor.name,
                    profilePath = createActor.profilePath,
                    biography = createActor.biography,
                    movieDetails = emptySet()
                )
            )

            RestActor(
                id = actor.id,
                actorMdbId = actor.actorMdbId,
                profilePath = actor.profilePath,
                name = actor.name
            )
        } else {
            throw IllegalArgumentException("An actor with an 'actorMdbId' of ${createActor.actorMdbId} already exists")
        }
    }


    // This cannot function until we first create a method to add an actor to a movie
    override fun getActorDetail(actorId: Int): RestActorDetail {
        val actor =  actorRepo.findById(actorId).get()
        val movieDetailIds = movieDetailRepo.findMovieIdsByActorId(actorId)
        val movieDetails = movieDetailRepo.findAllById(movieDetailIds)

        return RestActorDetail(
            id = actor.id,
            actorMdbId = actor.actorMdbId,
            biography = actor.biography,
            profilePath = actor.profilePath,
            images = emptyList(), // need to set up images
            restMovieCredits = movieDetails.map {
                RestMovieCredit(
                    id = it.id,
                    mDbId = it.mDbId,
                    posterPath = it.posterPath,
                    directors = emptyList(), // need to implement this?
                    genres = emptyList(), // look up how genres work in the api
                    popularity = it.popularity,
                    releaseDate = it.releaseDate,
                    revenue = it.revenue,
                    isFavourite = it.isFavourite
                ) }
        )
    }



    override fun getActors(actorIds: List<Int>): List<RestActor> {
        return actorRepo.findAllById(actorIds)
            .map { RestActor(id = it.id, actorMdbId = it.actorMdbId, name = it.name, profilePath = it.profilePath) }
    }

}
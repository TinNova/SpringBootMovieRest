package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.model.Actor
import com.tinnovakovic.springboot.fluttermovierest.model.MovieDetail
import com.tinnovakovic.springboot.fluttermovierest.repo.ActorRepo
import com.tinnovakovic.springboot.fluttermovierest.repo.MovieDetailRepo
import com.tinnovakovic.springboot.fluttermovierest.rest_models.*
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class ActorServiceImpl(
    private val actorRepo: ActorRepo,
    private val movieDetailRepo: MovieDetailRepo,
    private val userService: UserService
) : ActorService {

    override fun createActor(createActor: CreateActor): RestActor {
        val actor = createActor(
            Actor(
                id = createActor.id,
                actorMdbId = createActor.actorMdbId,
                name = createActor.name,
                profilePath = createActor.profilePath,
                biography = createActor.biography,
                movieDetails = emptySet(),
                appUsers = emptySet()
            )
        )

        return RestActor(
            id = actor.id,
            actorMdbId = actor.actorMdbId,
            profilePath = actor.profilePath,
            name = actor.name
        )
    }

    private fun createActor(actor: Actor): Actor {
        return if (actorRepo.findByActorMdbId(actor.actorMdbId).isEmpty) {
            // Add the correct checks here to make sure we're not saving an actor that already exists ect...
            actorRepo.save(actor)

        } else {
            throw IllegalArgumentException("An actor with an 'actorMdbId' of ${actor.actorMdbId} already exists")
        }
    }

    override fun bulkSaveActors(createActors: List<CreateActor>) {
        createActors.forEach { createActor ->
            if (actorRepo.findByActorMdbId(createActor.actorMdbId).isEmpty) {
                actorRepo.save(
                    Actor(
                        id = createActor.id,
                        actorMdbId = createActor.actorMdbId,
                        name = createActor.name,
                        profilePath = createActor.profilePath,
                        biography = createActor.biography,
                        movieDetails = emptySet(),
                        appUsers = emptySet()
                    )
                )
            } else {

                //This exception causes a crash, can we just log it instead
//                throw IllegalArgumentException("During Bulk Save Operation, an actor with a 'mDbId' of ${createActor.actorMdbId} already exists")
            }
        }
    }

    override fun getRestActorDetail(actorId: Int): RestActorDetail {
        return getActorDetail(actorId).let { actor ->
            RestActorDetail(id = actor.id,
                actorMdbId = actor.actorMdbId,
                biography = actor.biography,
                profilePath = actor.profilePath,
                images = null, // need to set up images
                restMovieCredits = actor.movieDetails.map {
                    RestMovieCredit(
                        id = it.id,
                        movieMDbId = it.mDbId,
                        posterPath = it.posterPath,
                        directors = emptyList(), // need to implement this?
                        genres = emptyList(), // look up how genres work in the api
                        popularity = it.popularity,
                        releaseDate = it.releaseDate,
                        revenue = it.revenue,
                        isFavourite = it.isFavourite
                    )
                })
        }
    }

    // This cannot function until we first create a method to add an actor to a movie
    private fun getActorDetail(actorId: Int): Actor {
        return actorRepo.findById(actorId).get()
    }

    override fun getRestActors(actorIds: List<Int>): List<RestActor> {
        return getActors(actorIds).map {
            RestActor(
                id = it.id,
                actorMdbId = it.actorMdbId,
                name = it.name,
                profilePath = it.profilePath
            )
        }
    }

    override fun getActors(actorIds: List<Int>): List<Actor> {
        return actorRepo.findAllById(actorIds)
    }

    override fun getFavouriteActors(userId: Int): List<RestActorDetail> {
        val user: RestAppUser = userService.getRestAppUser(userId)

        return getActorsById(user.actors).map { actor ->
            RestActorDetail(id = actor.id,
                actorMdbId = actor.actorMdbId,
                biography = actor.biography,
                profilePath = actor.profilePath,
                images = null, // need to set up images
                restMovieCredits = actor.movieDetails.map {
                    RestMovieCredit(
                        id = it.id,
                        movieMDbId = it.mDbId,
                        posterPath = it.posterPath,
                        directors = emptyList(), // need to implement this?
                        genres = emptyList(), // look up how genres work in the api
                        popularity = it.popularity,
                        releaseDate = it.releaseDate,
                        revenue = it.revenue,
                        isFavourite = it.isFavourite
                    )
                })
        }
    }

    private fun getActorsById(ids: Set<Int>): List<Actor> = actorRepo.findAllById(ids)

}
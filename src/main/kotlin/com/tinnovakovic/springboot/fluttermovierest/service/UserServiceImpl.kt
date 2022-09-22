package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.model.Actor
import com.tinnovakovic.springboot.fluttermovierest.model.AppUser
import com.tinnovakovic.springboot.fluttermovierest.model.AppUserDetail
import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import com.tinnovakovic.springboot.fluttermovierest.repo.*
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestActor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestAppUser
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovie
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestSaveActor
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class UserServiceImpl(
    private val userRepo: UserRepo,
    private val userDetailRepo: AppUserDetailRepo,
    private val movieRepo: MovieRepo,
    private val movieDetailRepo: MovieDetailRepo,
    private val actorRepo: ActorRepo
) : UserService {

    override fun getRestAppUsers(): List<RestAppUser> {
        return getUsers().map {
            RestAppUser(
                id = it.id,
                username = it.username,
                email = it.email,
                movies = it.favMovies.map { it.id }.toSet(),
                actors = it.favActors.map { it.id }.toSet(),
            )
        }
    }

    // to return movieIds you need to query the app_user_movie table, but we only want to do this in AppUserDetail
    private fun getUsers(): List<AppUser> = userRepo.findAll()

    //    override fun getUser(id: Int): RestAppUser {
    override fun getRestAppUser(id: Int): RestAppUser {
        return getAppUser(id).let {
            RestAppUser(
                id = it.id,
                username = it.username,
                email = it.email,
                movies = it.favMovies.map { it.id }.toMutableSet()
            )
        }
    }

    // to return movieIds you need to query the app_user_movie table, but we only want to do this in AppUserDetail
    private fun getAppUser(id: Int): AppUser {
        userRepo.findById(id).let {
            return if (it.isPresent) {
                it.get()
            } else {
                throw NoSuchElementException("Could not find a user with an id of $id.")
            }
        }
    }

    override fun createUser(restAppUser: RestAppUser): RestAppUser {
        return createUser(
            AppUser(
                id = -1, username = restAppUser.username, email = restAppUser.email, appUserDetail = AppUserDetail(
                    id = -1, username = restAppUser.username, email = restAppUser.email, reviews = emptySet()
                ), favMovies = emptySet(), favActors = emptySet()
            )
        ).let {
            restAppUser.copy(id = it.id)
        }
    }

    private fun createUser(appUser: AppUser): AppUser {
        return if (userRepo.findByEmail(appUser.email).isEmpty) {
            userRepo.save(appUser)
        } else {
            throw IllegalArgumentException("A user with the 'email' ${appUser.email} already exists")
        }
    }

    // prevent changing email and username. Unneeded feature
//    override fun updateUser(restAppUser: RestAppUser): RestAppUser {
//        userRepo.findById(restAppUser.id).let {
//            return if (it.isPresent) {
//                userRepo.save(
//                    AppUser(
//                        id = restAppUser.id,
//                        username = restAppUser.username,
//                        email = restAppUser.email,
//                        movies = it.get().movies
//                    )
//                )
//
//                restAppUser
//            } else {
//                throw NoSuchElementException("Could not find a user with an 'email' of ${restAppUser.email}.")
//            }
//        }
//    }

    override fun deleteUser(id: Int) {
        if (userRepo.findById(id).isPresent) {
            userRepo.deleteById(id)
        } else {
            throw NoSuchElementException("Could not find a user with an 'id' of ${id}.")
        }
    }

    override fun saveMovie(userId: Int, restMovie: RestMovie): RestMovie {
        movieRepo.findById(restMovie.id).let { entityMovie ->
            if (entityMovie.isPresent) {
                userRepo.findById(userId).let { entityUser ->
                    return if (entityUser.isPresent) {
                        val userEntityMovies: Set<Movie> = entityUser.get().favMovies.plus(entityMovie.get())
                        userRepo.save(entityUser.get().copy(favMovies = userEntityMovies))
                        restMovie
                    } else {
                        throw NoSuchElementException("Could not find a user with an 'id' of ${userId}.")
                    }
                }
            } else {
                throw NoSuchElementException("Could not find a movie with an 'id' of ${restMovie.id}.")
            }
        }
    }

    override fun saveActor(userId: Int, actor: RestSaveActor): Boolean {
        actorRepo.findById(actor.id).let { entityActor ->
            if (entityActor.isPresent) {
                userRepo.findById(userId).let { entityUser ->
                    return if (entityUser.isPresent) {
                        val userEntityActors: Set<Actor> = entityUser.get().favActors.plus(entityActor.get())
                        userRepo.save(entityUser.get().copy(favActors = userEntityActors))
                        true
                    } else {
                        throw NoSuchElementException("Could not find a user with an 'id' of ${userId}.")
                    }
                }
            } else {
                throw NoSuchElementException("Could not find an actor with an 'id' of ${actor.id}.")
            }
        }
    }
}
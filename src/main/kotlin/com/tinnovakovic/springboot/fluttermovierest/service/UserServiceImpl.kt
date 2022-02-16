package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.repo.MovieRepo
import com.tinnovakovic.springboot.fluttermovierest.repo.UserRepo
import com.tinnovakovic.springboot.fluttermovierest.model.AppUser
import com.tinnovakovic.springboot.fluttermovierest.model.AppUserDetail
import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import com.tinnovakovic.springboot.fluttermovierest.repo.AppUserDetailRepo
import com.tinnovakovic.springboot.fluttermovierest.repo.MovieDetailRepo
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestAppUser
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovie
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class UserServiceImpl(
    private val userRepo: UserRepo,
    private val userDetailRepo: AppUserDetailRepo,
    private val movieRepo: MovieRepo,
    private val movieDetailRepo: MovieDetailRepo
) : UserService {

    // to return movieIds you need to query the app_user_movie table, but we only want to do this in AppUserDetail
    override fun getUsers(): List<RestAppUser> {
        return userRepo.findAll().map {
            RestAppUser(
                id = it.id,
                username = it.username,
                email = it.email,
                movies = it.favMovies.map { it.id }.toMutableSet()
            )
        }
    }

    // to return movieIds you need to query the app_user_movie table, but we only want to do this in AppUserDetail
    override fun getUser(id: Int): RestAppUser {
        userRepo.findById(id).let {
            return if (it.isPresent) {
                RestAppUser(
                    id = it.get().id,
                    username = it.get().username,
                    email = it.get().email,
                    movies = it.get().favMovies.map { it.id }.toMutableSet()
                )
            } else {
                throw NoSuchElementException("Could not find a user with an id of $id.")
            }
        }
    }

    override fun createUser(restAppUser: RestAppUser): RestAppUser {
        return if (userRepo.findById(restAppUser.id).isEmpty) { //this check has to be done by email or username,
            // because the user will not provide a SQL id when creating an account
            val appUser = userRepo.save(
                AppUser(
                    id = -1,
                    username = restAppUser.username,
                    email = restAppUser.email,
                    appUserDetail = AppUserDetail(
                        id = -1,
                        username = restAppUser.username,
                        email = restAppUser.email
                    ),
                    favMovies = emptySet()
                )
            )
            restAppUser.copy(id = appUser.id)
        } else {
            throw IllegalArgumentException("A user with the 'email' ${restAppUser.email} already exists")
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

    override fun saveMovie(id: Int, restMovie: RestMovie): RestMovie {
        movieRepo.findById(restMovie.id).let { entityMovie ->
            if (entityMovie.isPresent) {
                userRepo.findById(id).let { entityUser ->
                    return if (entityUser.isPresent) {
                        val userEntityMovies: Set<Movie> = entityUser.get().favMovies.plus(entityMovie.get())
                        userRepo.save(entityUser.get().copy(favMovies = userEntityMovies))
                        restMovie
                    } else {
                        throw NoSuchElementException("Could not find a user with an 'id' of ${id}.")
                    }
                }
            } else {
                throw NoSuchElementException("Could not find a movie with an 'id' of ${restMovie.id}.")
            }
        }
    }
}
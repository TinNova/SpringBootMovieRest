package com.tinnovakovic.springboot.fluttermovierest.datasource

import com.tinnovakovic.springboot.fluttermovierest.model.AppUser
import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestAppUser
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovie
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class UserDataSourceImpl(private val userRepo: UserRepo, private val movieRepo: MovieRepo) : UserDataSource {

    override fun retrieveUsers(): List<RestAppUser> {
        return userRepo.findAll().map {
            RestAppUser(id = it.id, username = it.username, email = it.email)
        }
    }

    override fun retrieveUser(id: Int): RestAppUser {
        userRepo.findById(id).let {
            return if (it.isPresent) {
                RestAppUser(id = it.get().id, username = it.get().username, email = it.get().email)
            } else {
                throw NoSuchElementException("Could not find a user with an id of $id.")
            }
        }
    }

    override fun createUser(restAppUser: RestAppUser): RestAppUser {
        return if (userRepo.findById(restAppUser.id).isEmpty) {
            val savedMovie = userRepo.save(
                AppUser(
                    id = restAppUser.id,
                    username = restAppUser.username,
                    email = restAppUser.email,
                    movies = emptySet()
                )
            )

            restAppUser.copy(id = savedMovie.id)
        } else {
            throw IllegalArgumentException("A user with the 'email' ${restAppUser.email} already exists")
        }
    }

    override fun updateUser(restAppUser: RestAppUser): RestAppUser {
        userRepo.findById(restAppUser.id).let {
            return if (it.isPresent) {
                userRepo.save(
                    AppUser(
                        id = restAppUser.id,
                        username = restAppUser.username,
                        email = restAppUser.email,
                        movies = it.get().movies
                    )
                )

                restAppUser
            } else {
                throw NoSuchElementException("Could not find a user with an 'email' of ${restAppUser.email}.")
            }
        }
    }

    override fun deleteUser(id: Int) {
        if (userRepo.findById(id).isPresent) {
            userRepo.deleteById(id)
        } else {
            throw NoSuchElementException("Could not find a user with an 'id' of ${id}.")
        }
    }

    override fun saveMovie(id: Int, restMovie: RestMovie): RestMovie {

        // get the SqlMovie for the Movie we want to save to User
        val sqlMovie: Movie = movieRepo.findById(restMovie.id).get()

        // get the sqlUser
        userRepo.findById(id).let {
            return if (it.isPresent) {
                // get the movies currently in the user
                val userSqlMovies: MutableSet<Movie> = it.get().movies as MutableSet<Movie>
                userSqlMovies.add(sqlMovie)
                userRepo.save(
                    AppUser(
                        id = it.get().id,
                        username = it.get().username,
                        email = it.get().email,
                        movies = userSqlMovies
                    )
                )

                restMovie
            } else {
                throw NoSuchElementException("Could not find a user with an 'id' of ${id}.")

            }
        }
    }
}
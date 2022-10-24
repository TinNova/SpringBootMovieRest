package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.model.*
import com.tinnovakovic.springboot.fluttermovierest.repo.*
import com.tinnovakovic.springboot.fluttermovierest.rest_models.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Service
@Transactional
class UserServiceImpl(
    private val userRepo: UserRepo,
    private val userDetailRepo: AppUserDetailRepo,
    private val movieRepo: MovieRepo,
    private val movieDetailRepo: MovieDetailRepo,
    private val actorRepo: ActorRepo,
    private val roleRepo: RoleRepo,
    private val passwordEncoder: PasswordEncoder
) : UserService, UserDetailsService {

    val log: Logger = LoggerFactory.getLogger(UserService::class.java)

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username != null) {
            val user = userRepo.findByUsername(username)
            return if (user.isPresent) {
                getUserDetails(user.get())
            } else {
                log.error("User not found in the database.")
                throw UsernameNotFoundException("User not found in the database.")
            }
        }
        throw UsernameNotFoundException("User not found in the database.")
    }

    private fun getUserDetails(appUser: AppUser): UserDetails {
        log.info("User found in the database: ${appUser.email}")

        val authorities: List<SimpleGrantedAuthority> = appUser.roles.map {
            (SimpleGrantedAuthority(it.name))
        }

        return org.springframework.security.core.userdetails.User(appUser.username, appUser.password, authorities)
    }

    override fun getRestAppUsers(): List<RestAppUser> {
        return getUsers().map {
            RestAppUser(
                id = it.id,
                username = it.username,
                email = it.email,
                password = it.password,
                movies = it.favMovies.map { it.id }.toSet(),
                actors = it.favActors.map { it.id }.toSet(),
                roles = it.roles.map { RestRole(id = it.id, name = it.name) }.toSet(),
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
                password = it.password,
                movies = it.favMovies.map { it.id }.toSet(),
                actors = it.favActors.map { it.id }.toSet(),
                roles = it.roles.map { RestRole(id = it.id, name = it.name) }.toSet(),
            )
        }
    }

    // to return movieIds you need to query the app_user_movie table, but we only want to do this in AppUserDetail
    override fun getAppUser(id: Int): AppUser {
        userRepo.findById(id).let {
            return if (it.isPresent) {
                it.get()
            } else {
                throw NoSuchElementException("Could not find a user with an id of $id.")
            }
        }
    }

    override fun getAppUser(username: String): AppUser {
        userRepo.findByUsername(username).let {
            return if (it.isPresent) {
                it.get()
            } else {
                throw NoSuchElementException("Could not find a user with an id of $username.")
            }
        }
    }

    override fun saveUser(restAppUser: RestAppUser): RestAppUser {
        return createUser(
            AppUser(
                id = -1,
                username = restAppUser.username,
                email = restAppUser.email,
                password = restAppUser.password,
                appUserDetail = AppUserDetail(
                    id = -1, username = restAppUser.username, email = restAppUser.email, reviews = emptySet()
                ),
                favMovies = emptySet(),
                favActors = emptySet(),
                roles = emptySet()
            )
        ).let {
            restAppUser.copy(id = it.id)
        }
    }

    private fun createUser(appUser: AppUser): AppUser {
        return if (userRepo.findByEmail(appUser.email).isEmpty) {
            appUser.password = passwordEncoder.encode(appUser.password)
            userRepo.save(appUser)
        } else {
            throw IllegalArgumentException("A user with the 'email' ${appUser.email} already exists")
        }
    }

    override fun deleteUser(id: Int) {
        if (userRepo.findById(id).isPresent) {
            userRepo.deleteById(id)
        } else {
            throw NoSuchElementException("Could not find a user with an 'id' of ${id}.")
        }
    }

    override fun saveMovieToUser(userId: Int, movie: RestSaveMovie): RestSaveMovieResult {
        movieRepo.findById(movie.id).let { entityMovie ->
            if (entityMovie.isPresent) {
                userRepo.findById(userId).let { entityUser ->
                    return if (entityUser.isPresent) {

                        val saved: List<Movie> = entityUser.get().favMovies.filter { it.id == movie.id }

                        if (saved.isNotEmpty()) {
                            // delete movie from user favourites
                            userRepo.save(
                                entityUser.get().copy(favMovies = entityUser.get().favMovies.minus(entityMovie.get()))
                            )
                            RestSaveMovieResult(saved = DELETED)
                        } else {
                            // add movie to user favourites
                            userRepo.save(
                                entityUser.get().copy(favMovies = entityUser.get().favMovies.plus(entityMovie.get()))
                            )
                            RestSaveMovieResult(saved = SAVED)
                        }
                    } else {
                        throw NoSuchElementException("Could not find a user with an 'id' of ${userId}.")
                    }
                }
            } else {
                throw NoSuchElementException("Could not find a movie with an 'id' of ${movie.id}.")
            }
        }
    }

    //TODO Delete an Actor if it's already saved to this user
    override fun saveActorToUser(userId: Int, actor: RestSaveActor): Boolean {
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

    // Add check to make sure it exists before returning
    override fun saveRole(role: Role): Role {
        log.info("TINTIN - Role Saved: Role Id: ${role.id}, Role Name: ${role.name}")
        return roleRepo.save(role)
    }


    // Add checks if user or role do not exist
    override fun addRoleToUser(email: String, roleName: String) {
        val user = userRepo.findByEmail(email)
        val role = roleRepo.findByName(roleName)
        val existingRoles: MutableSet<Role> = user.get().roles as MutableSet<Role>
        existingRoles.add(role)
        userRepo.save(user.get().copy(roles = existingRoles))

    }
}

const val SAVED = "saved"
const val DELETED = "deleted"

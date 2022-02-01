package com.tinnovakovic.springboot.fluttermovierest.datasource

import com.tinnovakovic.springboot.fluttermovierest.model.AppUser
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class UserDataSourceImpl(private val userRepo: UserRepo) : UserDataSource {

    override fun retrieveUsers(): List<AppUser> {
        return userRepo.findAll()
    }

    override fun retrieveUser(id: Int): AppUser {
        userRepo.findById(id).let {
            return if (it.isPresent) {
                it.get()
            } else {
                throw NoSuchElementException("Could not find a user with an id of $id.")
            }
        }
    }

    override fun createUser(appUser: AppUser): AppUser {
        return if (userRepo.findById(appUser.id).isEmpty) {
            userRepo.save(appUser)
        } else {
            throw IllegalArgumentException("A user with the 'email' ${appUser.email} already exists")
        }
    }

    override fun updateUser(appUser: AppUser): AppUser {
        return if (userRepo.findById(appUser.id).isPresent) {
            userRepo.save(appUser)
        } else {
            throw NoSuchElementException("Could not find a user with an 'email' of ${appUser.email}.")
        }
    }

    override fun deleteUser(id: Int) {
        if (userRepo.findById(id).isPresent) {
            userRepo.deleteById(id)
        } else {
            throw NoSuchElementException("Could not find a user with an 'id' of ${id}.")
        }
    }
}
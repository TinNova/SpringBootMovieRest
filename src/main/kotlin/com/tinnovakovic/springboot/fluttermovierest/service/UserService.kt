package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestAppUser
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovie

interface UserService {

    fun getUsers(): List<RestAppUser>
    fun getUser(id: Int): RestAppUser
    fun createUser(restAppUser: RestAppUser): RestAppUser
//    fun updateUser(restAppUser: RestAppUser): RestAppUser
    fun deleteUser(id: Int): Unit
    fun saveMovie(userId: Int, restMovie: RestMovie): RestMovie
}
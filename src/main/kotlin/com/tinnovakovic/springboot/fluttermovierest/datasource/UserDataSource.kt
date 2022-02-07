package com.tinnovakovic.springboot.fluttermovierest.datasource

import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestAppUser
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovie

interface UserDataSource {

    fun retrieveUsers(): List<RestAppUser>
    fun retrieveUser(id: Int): RestAppUser
    fun createUser(restAppUser: RestAppUser): RestAppUser
    fun updateUser(restAppUser: RestAppUser): RestAppUser
    fun deleteUser(id: Int)
    fun saveMovie(id: Int, restMovie: RestMovie): RestMovie

}
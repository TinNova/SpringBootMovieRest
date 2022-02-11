package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestAppUser
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovie
import org.springframework.stereotype.Service

@Service
interface UserService {

    fun getUsers(): List<RestAppUser>
    fun getUser(id: Int): RestAppUser
    fun createUser(restAppUser: RestAppUser): RestAppUser
//    fun updateUser(restAppUser: RestAppUser): RestAppUser
    fun deleteUser(id: Int): Unit
    fun saveMovie(id: Int, restMovie: RestMovie): RestMovie
}
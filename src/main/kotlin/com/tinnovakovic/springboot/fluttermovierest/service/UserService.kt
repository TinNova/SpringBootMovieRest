package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.datasource.UserDataSource
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestAppUser
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestMovie
import org.springframework.stereotype.Service

@Service
class UserService(private val dataSource: UserDataSource) {

    fun getUsers(): List<RestAppUser> = dataSource.retrieveUsers()
    fun getUser(id: Int): RestAppUser = dataSource.retrieveUser(id)
    fun createUser(restAppUser: RestAppUser): RestAppUser = dataSource.createUser(restAppUser)
    fun updateUser(restAppUser: RestAppUser): RestAppUser = dataSource.updateUser(restAppUser)
    fun deleteUser(id: Int): Unit = dataSource.deleteUser(id)
    fun saveMovie(id: Int, restMovie: RestMovie): RestMovie = dataSource.saveMovie(id, restMovie)
}
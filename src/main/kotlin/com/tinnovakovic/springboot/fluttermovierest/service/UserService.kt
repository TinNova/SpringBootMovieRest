package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.model.AppUser
import com.tinnovakovic.springboot.fluttermovierest.model.Role
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestAppUser
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestSaveActor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestSaveMovie
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestSaveMovieResult

interface UserService {

    fun getRestAppUsers(): List<RestAppUser>
    fun getRestAppUser(id: Int): RestAppUser
    fun getAppUser(id: Int): AppUser
    fun getAppUser(userName: String): AppUser
    fun saveUser(restAppUser: RestAppUser): RestAppUser
    fun deleteUser(id: Int): Unit
    fun saveMovieToUser(userId: Int, restSaveMovie: RestSaveMovie): RestSaveMovieResult
    fun saveActorToUser(userId: Int, restSaveActor: RestSaveActor): Boolean
    fun saveRole(role: Role): Role
    fun addRoleToUser(email: String, roleName: String)
}

package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.model.AppUser
import com.tinnovakovic.springboot.fluttermovierest.model.Role
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestAppUser
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestSaveActor
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestSaveMovie

interface UserService {

    fun getRestAppUsers(): List<RestAppUser>
    fun getRestAppUser(id: Int): RestAppUser
    fun getAppUser(id: Int): AppUser
    fun saveUser(restAppUser: RestAppUser): RestAppUser

    fun deleteUser(id: Int): Unit
    fun saveMovieToUser(userId: Int, restSaveMovie: RestSaveMovie): Boolean
    fun saveActorToUser(userId: Int, restSaveActor: RestSaveActor): Boolean

    fun saveRole(role: Role): Role

    fun addRoleToUser(email: String, roleName: String)
}

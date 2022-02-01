package com.tinnovakovic.springboot.fluttermovierest.datasource

import com.tinnovakovic.springboot.fluttermovierest.model.AppUser

interface UserDataSource {

    fun retrieveUsers(): List<AppUser>
    fun retrieveUser(id: Int): AppUser
    fun createUser(appUser: AppUser): AppUser
    fun updateUser(appUser: AppUser): AppUser
    fun deleteUser(id: Int)

}
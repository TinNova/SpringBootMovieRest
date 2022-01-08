package com.tinnovakovic.springboot.fluttermovierest.datasource

import com.tinnovakovic.springboot.fluttermovierest.model.User

interface UserDataSource {

    fun retrieveUsers(): List<User>
    fun retrieveUser(email: String): User
    fun createUser(user: User): User
    fun updateUser(user: User): User
    fun deleteUser(email: String)

}
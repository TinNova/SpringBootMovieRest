package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.datasource.UserDataSource
import com.tinnovakovic.springboot.fluttermovierest.model.User
import org.springframework.stereotype.Service

@Service
class UserService(private val dataSource: UserDataSource) {

    fun getUsers(): List<User> = dataSource.retrieveUsers()
    fun getUser(email: String): User = dataSource.retrieveUser(email)
    fun createUser(user: User): User = dataSource.createUser(user)
    fun updateUser(user: User): User = dataSource.updateUser(user)
    fun deleteUser(email: String): Unit = dataSource.deleteUser(email)
}
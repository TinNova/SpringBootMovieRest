package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.datasource.UserDataSource
import com.tinnovakovic.springboot.fluttermovierest.model.AppUser
import org.springframework.stereotype.Service

@Service
class UserService(private val dataSource: UserDataSource) {

    fun getUsers(): List<AppUser> = dataSource.retrieveUsers()
    fun getUser(id: Int): AppUser = dataSource.retrieveUser(id)
    fun createUser(appUser: AppUser): AppUser = dataSource.createUser(appUser)
    fun updateUser(appUser: AppUser): AppUser = dataSource.updateUser(appUser)
    fun deleteUser(id: Int): Unit = dataSource.deleteUser(id)
}
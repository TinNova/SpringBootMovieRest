package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.datasource.UserDataSource
import com.tinnovakovic.springboot.fluttermovierest.model.AppUser
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class UserServiceTest {

    private val dataSource: UserDataSource = mockk(relaxed = true)
    private val sut = UserService(dataSource)

    @Test
    fun `WHEN getUsers() THEN trigger retrieveUsers()`() {
        //when
        sut.getUsers()

        //then
        verify(exactly = 1) { dataSource.retrieveUsers() }
    }

    @Test
    fun `WHEN getUser() THEN trigger retrieveUser()`() {
        //given
        val id = 1

        //when
        sut.getUser(id)

        //then
        verify(exactly = 1) { dataSource.retrieveUser(id) }
    }

    @Test
    fun `WHEN createUser() THEN trigger createUser`() {
        //given
        val appUser: AppUser = mockk()

        //when
        sut.createUser(appUser)

        //then
        verify(exactly = 1) { dataSource.createUser(appUser) }
    }

    @Test
    fun `WHEN updateUser() THEN trigger updateUser`() {
        //given
        val appUser: AppUser = mockk()

        //when
        sut.updateUser(appUser)

        //then
        verify(exactly = 1) { dataSource.updateUser(appUser) }
    }

    @Test
    fun `WHEN deleteUser() THEN trigger deleteUser`() {
        //given
        val id = 1

        //when
        sut.deleteUser(id)

        //then
        verify(exactly = 1) { dataSource.deleteUser(id) }
    }
}
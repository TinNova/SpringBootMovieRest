package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.datasource.UserDataSource
import com.tinnovakovic.springboot.fluttermovierest.model.User
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
        val email = "email@gmail.com"

        //when
        sut.getUser(email)

        //then
        verify(exactly = 1) { dataSource.retrieveUser(email) }
    }

    @Test
    fun `WHEN createUser() THEN trigger createUser`() {
        //given
        val user: User = mockk()

        //when
        sut.createUser(user)

        //then
        verify(exactly = 1) { dataSource.createUser(user) }
    }

    @Test
    fun `WHEN updateUser() THEN trigger updateUser`() {
        //given
        val user: User = mockk()

        //when
        sut.updateUser(user)

        //then
        verify(exactly = 1) { dataSource.updateUser(user) }
    }

    @Test
    fun `WHEN deleteUser() THEN trigger deleteUser`() {
        //given
        val email = "email"

        //when
        sut.deleteUser(email)

        //then
        verify(exactly = 1) { dataSource.deleteUser(email) }
    }
}
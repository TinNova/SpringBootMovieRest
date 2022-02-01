package com.tinnovakovic.springboot.fluttermovierest.datasource

import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class UserDataSourceImplTest {

    private val userRepo: UserRepo = mockk(relaxed = true)
    private val sut = UserDataSourceImpl(userRepo)

    @Test
    fun `WHEN retrieveUsers THEN all users are returned`() {
        // when
        val users = sut.retrieveUsers()

        // then
        Assertions.assertThat(users.size).isGreaterThanOrEqualTo(2)
    }
}
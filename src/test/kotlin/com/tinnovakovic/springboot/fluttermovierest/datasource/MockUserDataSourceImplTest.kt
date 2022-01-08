package com.tinnovakovic.springboot.fluttermovierest.datasource

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class MockUserDataSourceImplTest {

    private val mockUserDataSourceImpl = MockUserDataSourceImpl()

    @Test
    fun `WHEN retrieveUsers THEN all users are returned`() {
        // when
        val users = mockUserDataSourceImpl.retrieveUsers()

        // then
        Assertions.assertThat(users.size).isGreaterThanOrEqualTo(2)
    }
}
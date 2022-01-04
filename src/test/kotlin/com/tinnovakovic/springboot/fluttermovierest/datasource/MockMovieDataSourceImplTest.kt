package com.tinnovakovic.springboot.fluttermovierest.datasource

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockMovieDataSourceImplTest {

    private val mockMovieDataSourceImpl = MockMovieDataSourceImpl()

    @Test
    fun `should provide a list of movies`() {
        // when
        val movies = mockMovieDataSourceImpl.retrieveMovies()

        // then
        assertThat(movies.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock data`() {
        //when
        val movies = mockMovieDataSourceImpl.retrieveMovies()

        //then
        assertThat(movies).allMatch { it.id != 0 }
        assertThat(movies).allMatch { it.movieId != "" }
        assertThat(movies).allMatch { it.posterPath != "" }
    }
}
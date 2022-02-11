package com.tinnovakovic.springboot.fluttermovierest.datasource

import com.tinnovakovic.springboot.fluttermovierest.repo.MovieRepo
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MovieDataSourceImplTest {

    private val movieRepo: MovieRepo = mockk(relaxed = true)
    private val sut = MovieDataSourceImpl(movieRepo)

    @Test
    fun `should provide a list of movies`() {
        // when
        val movies = sut.retrieveMovies()

        // then
        assertThat(movies.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock data`() {
        //when
        val movies = sut.retrieveMovies()

        //then
        assertThat(movies).allMatch { it.id != 0 }
        assertThat(movies).allMatch { it.movieId != "" }
        assertThat(movies).allMatch { it.posterPath != "" }
    }
}
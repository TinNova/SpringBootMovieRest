package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.datasource.MovieDataSource
import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import com.tinnovakovic.springboot.fluttermovierest.model.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.sql.DriverManager.println
import java.util.Collections.emptyList

internal class MovieServiceTest {

    private val dataSource: MovieDataSource = mockk(relaxed = true)
    private val sut = MovieService(dataSource)

    @Test
    fun `WHEN getMovies() THEN trigger retrieveMovies()`() {
        //when
        sut.getMovies()

        //then
        verify(exactly = 1) { dataSource.retrieveMovies() }
    }


    @Test
    fun `WHEN getMovie() THEN trigger retrieveMovie()`() {
        //given
        val id = 1

        //when
        sut.getMovie(id)

        //then
        verify(exactly = 1) { dataSource.retrieveMovie(id) }
    }

    @Test
    fun `WHEN createMovie() THEN trigger createMovie()`() {
        //given
        val movie: Movie = mockk()

        //when
        sut.createMovie(movie)

        //then
        verify(exactly = 1) { dataSource.createMovie(movie) }
    }

    @Test
    fun `WHEN updateMovie() THEN trigger updateMovie`() {
        //given
        val movie: Movie = mockk()

        //when
        sut.updateMovie(movie)

        //then
        verify(exactly = 1) { dataSource.updateMovie(movie) }
    }

    @Test
    fun `WHEN deleteMovie() THEN trigger deleteMovie`() {
        //given
        val id = 1

        //when
        sut.deleteMovie(id)

        //then
        verify(exactly = 1) { dataSource.deleteMovie(id) }
    }
}
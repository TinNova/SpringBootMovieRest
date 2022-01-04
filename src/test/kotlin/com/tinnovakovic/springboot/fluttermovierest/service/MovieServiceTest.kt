package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.datasource.MovieDataSource
import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.sql.DriverManager.println
import java.util.Collections.emptyList

internal class MovieServiceTest {

    private val dataSource: MovieDataSource = mockk(relaxed = true)
    private val movieService = MovieService(dataSource)

    @Test
    fun `should call it's data source and retrieve movies`() {
        //given

        //when
        movieService.getMovies()

        //then
        verify(exactly = 1) { dataSource.retrieveMovies() }
        
    }
}
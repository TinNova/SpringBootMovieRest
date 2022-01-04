package com.tinnovakovic.springboot.fluttermovierest.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

// Integration Test
@SpringBootTest
@AutoConfigureMockMvc
internal class MovieControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    @Nested
    @DisplayName("GET api/movies")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {

        @Test
        fun `should return all movies`() {
            //when //then
            mockMvc.get("/api/movies")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].id") { value(1) }
                    jsonPath("$[0].movieId") { value("1001") }
                    jsonPath("$[0].posterPath") { value("posterPath") }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/movies/{id}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should return movie with given id number`() {
            //given
            val id = 1

            //when //then
            mockMvc.get("/api/movies/${id}")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.id") { value("1") }
                    jsonPath("$.movieId") { value("1001") }
                    jsonPath("$.posterPath") { value("posterPath") }
                }
        }

        @Test
        fun `should return NOT FOUND when given id number does not exist`() {
            //given
            val id = -5

            //when //then
            mockMvc.get("/api/movies/${id}")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }

    @Nested
    @DisplayName("POST /api/movie")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostMovie {

        @Test
        fun `should add new movie`() {
            //given
            val newMovie = Movie(4, "1004", "posterPath4")

            //when
            val performPost = mockMvc.post("/api/movies") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newMovie)
            }

            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    jsonPath("$.id") { value("4") }
                    jsonPath("$.movieId") { value("1004") }
                    jsonPath("$.posterPath") { value("posterPath4") }
                }
        }
    }

    @Test
    fun `should return BAD REQUEST if movie with given 'id' already exists`() {
        //given
        val invalidMovie = Movie(1, "1001", "posterPath")

        //when
        val performPost = mockMvc.post("/api/movies") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalidMovie)
        }

        //then
        performPost
            .andDo { print() }
            .andExpect {
                status { isBadRequest() }
            }
    }

    @Nested
    @DisplayName("PATCH /api/movie")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchMovie {

        @Test
        fun `should update an existing movie`() {
            //given
            val updatedMovie = Movie(1, "1011", "posterPath")

            //when
            val performPatch = mockMvc.patch("/api/movies") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedMovie)
            }

            //then
            performPatch
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        jsonPath("$.id") { value(updatedMovie.id) }
                        jsonPath("$.movieId") { value(updatedMovie.movieId) }
                        jsonPath("$.posterPath") { value(updatedMovie.posterPath) }
                    }
                }

            mockMvc.get("/api/movies")
                .andExpect {
                    jsonPath("$[0].id") { value(updatedMovie.id) }
                    jsonPath("$[0].movieId") { value(updatedMovie.movieId) }
                    jsonPath("$[0].posterPath") { value(updatedMovie.posterPath) }
                }
        }

        @Test
        fun `should return BAD REQUEST if movie with given id doesn't exist`() {
            //given
            val invalidMovie = Movie(-5, "minus5", "posterPathMinus5")

            //when
            val performPatchRequest = mockMvc.patch("/api/movies") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidMovie)
            }

            //then
            performPatchRequest
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("DELETE /api/movie/{id}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteMovie {

        @Test
        fun `should delete the movie with the given id`() {
            //given
            val id = 1

            //when //then
            mockMvc.delete("/api/movies/$id")
                .andDo { print() }
                .andExpect { status { isNoContent() } }

            mockMvc.get("/api/movies/$id")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }

        @Test
        fun `should return NOT FOUND if the movie with the given id doesn't exist`() {
            //given
            val id = -5

            //when //then
            mockMvc.delete("/api/movies/$id")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }
}
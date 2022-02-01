package com.tinnovakovic.springboot.fluttermovierest.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import com.tinnovakovic.springboot.fluttermovierest.model.AppUser
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
internal class UserControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    @Nested
    @DisplayName("GET api/users")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetUsers {

        @Test
        fun `should return all users`() {
            //when //then
            mockMvc.get("/api/users/")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].id") { value(1) }
                    jsonPath("$[0].username") { value("tin") }
                    jsonPath("$[0].email") { value("tin@gmail.com") }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/users/{email}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAppUser {
        @Test
        fun `should return user with given email`() {
            //given
            val email = "tin@gmail.com"

            //when //then
            mockMvc.get("/api/users/${email}")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.id") { value("1") }
                    jsonPath("$.username") { value("tin") }
                    jsonPath("$.email") { value("tin@gmail.com") }
                }
        }

        @Test
        fun `should return NOT FOUND when given email does not exist`() {
            //given
            val email = "fake@gmail.com"

            //when //then
            mockMvc.get("/api/users/${email}")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }

    @Nested
    @DisplayName("POST /api/users")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostUser {

        @Test
        fun `should add new user`() {
            //given
            val newAppUser = AppUser(3, "mama", "mama@email.com")

            //when
            val performPost = mockMvc.post("/api/users/") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newAppUser)
            }

            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    jsonPath("$.id") { value(newAppUser.id) }
                    jsonPath("$.username") { value(newAppUser.username) }
                    jsonPath("$.email") { value(newAppUser.email) }
                }
        }


        @Test
        fun `should return BAD REQUEST if movie with given 'id' already exists`() {
            //given
            val invalidUser = Movie(1, "tin", "tin@gmail.com")

            //when
            val performPost = mockMvc.post("/api/users/") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidUser)
            }

            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }

    @Nested
    @DisplayName("PATCH /api/users")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchUser {

        @Test
        fun `should update an existing user`() {
            //given
            val updatedAppUser = AppUser(1, "updatedTin", "tin@gmail.com")

            //when
            val performPatch = mockMvc.patch("/api/users/") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedAppUser)
            }

            //then
            performPatch
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        jsonPath("$.id") { value(updatedAppUser.id) }
                        jsonPath("$.username") { value(updatedAppUser.username) }
                        jsonPath("$.email") { value(updatedAppUser.email) }
                    }
                }

            mockMvc.get("/api/users/")
                .andExpect {
                    jsonPath("$[0].id") { value(updatedAppUser.id) }
                    jsonPath("$[0].username") { value(updatedAppUser.username) }
                    jsonPath("$[0].email") { value(updatedAppUser.email) }
                }
        }

        @Test
        fun `should return BAD REQUEST if user with given email doesn't exist`() {
            //given
            val invalidAppUser = AppUser(-5, "invalidMan", "invalid@email.com")

            //when
            val performPatchRequest = mockMvc.patch("/api/users/") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidAppUser)
            }

            //then
            performPatchRequest
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("DELETE /api/users/{email}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteMovie {

        @Test
        fun `should delete the user with the given email`() {
            //given
            val email = "tin@gmail.com"

            //when //then
            mockMvc.delete("/api/users/$email")
                .andDo { print() }
                .andExpect { status { isNoContent() } }

            mockMvc.get("/api/users/$email")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }

        @Test
        fun `should return NOT FOUND if the user with the given email doesn't exist`() {
            //given
            val email = "fake@email.com"

            //when //then
            mockMvc.delete("/api/users/$email")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }
}
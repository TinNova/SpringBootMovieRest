package com.tinnovakovic.springboot.fluttermovierest.rest_models

data class RestAppUser(
    val id: Int = -1,
    val username: String = "",
    val email: String = "",
    val movies: Set<Int> = setOf(),
    val actors: Set<Int> = setOf(),
)
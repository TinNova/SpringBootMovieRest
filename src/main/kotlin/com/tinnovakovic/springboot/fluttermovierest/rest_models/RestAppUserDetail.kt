package com.tinnovakovic.springboot.fluttermovierest.rest_models

data class RestAppUserDetail(
    val id: Int = -1,
    val username: String = "",
    val email: String = "",
    val favMovies: List<Int> = emptyList(),
    val reviews: Set<Int> = emptySet()
//    val favActors: List<Int> = emptyList(),
//    val favDirectors: List<Int> = emptyList(),
)
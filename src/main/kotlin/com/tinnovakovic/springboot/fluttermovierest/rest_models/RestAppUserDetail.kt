package com.tinnovakovic.springboot.fluttermovierest.rest_models

data class RestAppUserDetail(
    val id: Int = -1,
    val username: String = "",
    val email: String = "",
    val favMovies: List<Int> = emptyList(),
//    val favActors: List<Int> = emptyList(),
//    val favDirectors: List<Int> = emptyList(),
//    val reviews: List<Int> = emptyList()
)
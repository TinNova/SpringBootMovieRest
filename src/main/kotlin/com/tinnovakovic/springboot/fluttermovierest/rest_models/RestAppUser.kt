package com.tinnovakovic.springboot.fluttermovierest.rest_models

import com.tinnovakovic.springboot.fluttermovierest.model.Role

data class RestAppUser(
    val id: Int = -1,
    val username: String = "",
    val email: String = "",
    var password: String,
    val movies: Set<Int> = setOf(),
    val actors: Set<Int> = setOf(),
    val roles: Set<RestRole> = setOf(),
)
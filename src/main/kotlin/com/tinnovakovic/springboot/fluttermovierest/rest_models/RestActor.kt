package com.tinnovakovic.springboot.fluttermovierest.rest_models

data class RestActor(
    val id: Int,
    val actorMdbId: String,
    val name: String,
    val profilePath: String
)

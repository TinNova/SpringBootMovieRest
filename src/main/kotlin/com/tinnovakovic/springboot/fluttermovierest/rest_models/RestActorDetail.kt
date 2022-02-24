package com.tinnovakovic.springboot.fluttermovierest.rest_models

data class RestActorDetail(
    val id: Int,
    val actorMdbId: String,
    val biography: String,
    val profilePath: String,
    val images: List<String>,
    val restMovieCredits: List<RestMovieCredit>
)

package com.tinnovakovic.springboot.fluttermovierest.rest_models

import com.tinnovakovic.springboot.fluttermovierest.model.MovieDetail

data class CreateActor(
    val id: Int,
    val actorMdbId: String,
    val name: String, // GetDetail
    val profilePath: String, // GetDetail
    val biography: String, // GetDetail
    val movieDetails: Set<MovieDetail>
)
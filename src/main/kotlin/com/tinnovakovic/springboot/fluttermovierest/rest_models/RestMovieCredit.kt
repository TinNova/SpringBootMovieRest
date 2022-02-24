package com.tinnovakovic.springboot.fluttermovierest.rest_models

data class RestMovieCredit(
    val id: Int,
    val mDbId: String,
    val posterPath: String,
    val directors: List<String>,
    val genres: List<String>,
    val popularity: Double,
    val releaseDate: String,
    val revenue: Double,
    val isFavourite: Boolean
)

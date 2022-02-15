package com.tinnovakovic.springboot.fluttermovierest.rest_models

data class RestMovieDetail(
    val id: Int,
    val mDbId: String,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val directors: String,
    val popularity: Double,
    val releaseDate: String,
    val revenue: Double,
    val runtime: String,
    val tagline: String,
    val voteAverage: Double,
    val voteCount: Int,
    val isFavourite: Boolean
    )

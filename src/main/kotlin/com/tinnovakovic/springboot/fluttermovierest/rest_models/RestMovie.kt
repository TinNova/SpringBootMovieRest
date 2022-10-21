package com.tinnovakovic.springboot.fluttermovierest.rest_models

class RestMovie(
    val id: Int,
    val mDbId: String,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
)
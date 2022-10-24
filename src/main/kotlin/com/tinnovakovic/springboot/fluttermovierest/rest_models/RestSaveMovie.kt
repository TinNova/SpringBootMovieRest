package com.tinnovakovic.springboot.fluttermovierest.rest_models

/**
 * RestSaveMovie used only for saving an Actor to a User
 **/
data class RestSaveMovie(
    val id: Int,
)

/**
 * RestSaveMovieResult used only for saving an Actor to a User
 * @param saved true means it was saved, false means it was deleted
 **/
data class RestSaveMovieResult(
    val saved: String,
)

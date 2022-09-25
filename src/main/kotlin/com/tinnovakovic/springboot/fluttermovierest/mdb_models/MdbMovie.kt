package com.tinnovakovic.springboot.fluttermovierest.mdb_models

data class MdbMoviesResult(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<MdbMovie>
)

data class MdbMovie(
    val vote_count: Int = 0,
    val id: Long = 0,
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val title: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val original_language: String = "",
    val original_title: String = "",
    val genre_ids: List<Int> = emptyList(),
    val backdrop_path: String = "",
    val adult: Boolean = false,
    val overview: String = "",
    val release_date: String = ""
)
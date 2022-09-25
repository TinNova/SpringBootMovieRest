package com.tinnovakovic.springboot.fluttermovierest.mdb_models

import java.math.BigDecimal

data class MdbMovieDetail(
    val id: Int = 0,
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val belongsToCollection: Any = Any(), //belongs_to_collection
    val budget: Long = 0,
    val genres: List<Genre> = listOf(),
    val homepage: String = "",
    val imdb_id: String? = "",
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val production_companies: List<ProductionCompany> = listOf(),
    val production_countries: List<ProductionCountry> = listOf(),
    val release_date: String = "",
    val revenue: Long = 0,
    val runtime: Int = 0,
    val spoken_languages: List<SpokenLanguage> = listOf(),
    val status: String = "",
    val tagline: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)

data class Genre(
    val id: Int,
    val name: String
)

data class ProductionCompany(
    val id: Int,
    val name: String,
    val origin_country: String,
    val logo_path: String? = ""
)

data class ProductionCountry(
    val iso_3166_1: String,
    val name: String
)

data class SpokenLanguage(
    val iso_639_1: String,
    val name: String
)
package com.tinnovakovic.springboot.fluttermovierest.mdb_models

data class MdbActorDetail(
    val birthday: String?,
    val known_for_department: String,
    val deathday: String?,
    val id: Int,
    val name: String,
    val also_known_as: List<String>,
    val gender: Int,
    val biography: String,
    val popularity: Double,
    val place_of_birth: String?,
    val profile_path: String?,
    val adult: Boolean,
    val homepage: String?
)


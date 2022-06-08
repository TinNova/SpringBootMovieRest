package com.tinnovakovic.springboot.fluttermovierest.mdb_models

data class MdbCredit(
    val id: Int,
    val cast: List<MdbCast> = emptyList(),
//    val crew: List<Crew> = emptyList()
)

data class MdbCast(
    val id: Int = -1,
    val cast_id: Int = -1,
    val character: String = "",
    val credit_id: String = "",
    val gender: Int = -1,
    val name: String = "",
    val order: Int = -1,
    val profile_path: String? = ""
)

//data class Crew(
//    val id: Int = -1,
//    val credit_id: String = "",
//    val name: String = "",
//    val job: String = "",
//    val profile_path: String = "",
//    val department: String = "",
//    val gender: Int = -1
//)

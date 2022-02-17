package com.tinnovakovic.springboot.fluttermovierest.repo

import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MovieRepo: JpaRepository<Movie, Int> {

    @Query(value = "SELECT * FROM MOVIE WHERE mdb_id = ?1", nativeQuery = true)
    fun findByMdbId(mDbId: String): Optional<Movie>

}
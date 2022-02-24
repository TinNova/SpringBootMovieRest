package com.tinnovakovic.springboot.fluttermovierest.repo

import com.tinnovakovic.springboot.fluttermovierest.model.Actor
import com.tinnovakovic.springboot.fluttermovierest.model.MovieDetail
import com.tinnovakovic.springboot.fluttermovierest.model.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MovieDetailRepo: JpaRepository<MovieDetail, Int> {

//    @Query(value = "SELECT * FROM MOVIE_DETAIL WHERE actor_mdb_id = ?1", nativeQuery = true)
//    fun findMoviesByActorId(actorMdbId: String): Optional<Actor>

    @Query(value = "SELECT movie_id FROM MOVIE_ACTOR WHERE actor_id = ?1", nativeQuery = true)
    fun findMovieIdsByActorId(actorId: Int): List<Int>
}
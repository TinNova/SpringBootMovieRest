package com.tinnovakovic.springboot.fluttermovierest.repo

import com.tinnovakovic.springboot.fluttermovierest.model.Actor
import com.tinnovakovic.springboot.fluttermovierest.model.Movie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ActorRepo : JpaRepository<Actor, Int> {

    @Query(value = "SELECT * FROM ACTOR WHERE actor_mdb_id = ?1", nativeQuery = true)
    fun findByActorMdbId(actorMdbId: String): Optional<Actor>

}
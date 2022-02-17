package com.tinnovakovic.springboot.fluttermovierest.repo

import com.tinnovakovic.springboot.fluttermovierest.model.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ReviewRepo: JpaRepository<Review, Int> {

    @Query(value = "SELECT * FROM REVIEW WHERE app_user_detail_id = ?1 AnD movie_detail_id = ?2 LIMIT 1", nativeQuery = true)
    fun findByUserDetailIdAndMovieDetailId(userDetailId: Int, movieDetailId: Int): Optional<Review>
}
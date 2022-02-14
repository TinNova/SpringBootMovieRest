package com.tinnovakovic.springboot.fluttermovierest.repo

import com.tinnovakovic.springboot.fluttermovierest.model.MovieDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieDetailRepo: JpaRepository<MovieDetail, Int>
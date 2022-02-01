package com.tinnovakovic.springboot.fluttermovierest.model

import javax.persistence.*

@Entity
@Table(name = "movie", uniqueConstraints = [UniqueConstraint(name = "unique_movie_constraints", columnNames = ["movieId"])])
data class Movie(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    val id: Int = -1,
    @Column(name = "movieId", nullable = false, columnDefinition = "TEXT")
    val movieId: String = "",
    @Column(name = "posterPath", nullable = false, columnDefinition = "TEXT")
    val posterPath: String = ""
)
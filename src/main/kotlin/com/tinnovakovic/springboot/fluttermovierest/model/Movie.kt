package com.tinnovakovic.springboot.fluttermovierest.model

import javax.persistence.*

@Entity
// used for readability of the database
@Table(
    name = "movie",
    uniqueConstraints = [UniqueConstraint(name = "unique_movieId", columnNames = ["movieId"])]
)
data class Movie(
    @Id
    @SequenceGenerator(
        name = SEQUENCE,
        sequenceName = SEQUENCE,
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = SEQUENCE
    )
    @Column(
        name = "id",
        updatable = false
    )
    val id: Int = -1,

    @Column(
        name = "movieId",
        nullable = false,
        columnDefinition = "TEXT",
    )
    val movieId: String = "",

    @Column(
        name = "posterPath",
        nullable = false,
        columnDefinition = "TEXT"
    )
    val posterPath: String = ""
)

private const val SEQUENCE = "movie_sequence"
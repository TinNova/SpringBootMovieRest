package com.tinnovakovic.springboot.fluttermovierest.model

import javax.persistence.*

@Entity
@Table(
    name = "movie_detail",
    uniqueConstraints = [UniqueConstraint(name = "unique_movieDetail_constraints", columnNames = ["movieId"])]
)
data class MovieDetail(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int = -1,
    @Column(name = "movieId")
    val movieId: String = "",
    @Column(name = "title")
    val title: String = "",
    @Column(name = "overview")
    val overview: String = "",
    @Column(name = "posterPath")
    val posterPath: String = "",
    @Column(name = "backdropPath")
    val backdropPath: String = "",
    @Column(name = "directors")
    val directors: String = "",
    @Column(name = "popularity")
    val popularity: Double = -1.0,
    @Column(name = "releaseDate")
    val releaseDate: String = "",
    @Column(name = "revenue")
    val revenue: Double = -1.0,
    @Column(name = "runtime")
    val runtime: String = "",
    @Column(name = "tagline")
    val tagline: String = "",
    @Column(name = "voteAverage")
    val voteAverage: Double = -1.0,
    @Column(name = "voteCount")
    val voteCount: Int = -1,
    @Column(name = "isFavourite")
    val isFavourite: Boolean = false
)

//  Handle lists later
//    val genres: List<MDBGenre> = listOf()
//    val trailers: List<Trailer> =
//    val actors: List<Actor> =
//    val reviews: List<MDBReview> =
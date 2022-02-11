package com.tinnovakovic.springboot.fluttermovierest.model

import javax.persistence.*

@Entity
@Table(
    name = "movie_detail",
    uniqueConstraints = [UniqueConstraint(name = "unique_movieDetail_constraints", columnNames = ["movie_id"])]
)
data class MovieDetail(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int,
    @Column(name = "movie_id")
    val movieId: String = "",
    @Column(name = "title")
    val title: String,
    @Column(name = "overview")
    val overview: String,
    @Column(name = "poster_path")
    val posterPath: String = "",
    @Column(name = "backdrop_path")
    val backdropPath: String,
    @Column(name = "directors")
    val directors: String,
    @Column(name = "popularity")
    val popularity: Double,
    @Column(name = "release_date")
    val releaseDate: String,
    @Column(name = "revenue")
    val revenue: Double,
    @Column(name = "runtime")
    val runtime: String,
    @Column(name = "tagline")
    val tagline: String,
    @Column(name = "vote_average")
    val voteAverage: Double,
    @Column(name = "vote_count")
    val voteCount: Int,
    @Column(name = "is_favourite")
    val isFavourite: Boolean,
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH]
    )
    @JoinTable(
        name = "appUserDetail_movieDetail",
        joinColumns = [JoinColumn(name = "movieDetail_id")],
        inverseJoinColumns = [JoinColumn(name = "appUserDetail_id")]
    )
    val appUsers: Set<AppUser>
)

//  Handle lists later
//    val genres: List<MDBGenre> = listOf()
//    val trailers: List<Trailer> =
//    val actors: List<Actor> =
//    val reviews: List<MDBReview> =
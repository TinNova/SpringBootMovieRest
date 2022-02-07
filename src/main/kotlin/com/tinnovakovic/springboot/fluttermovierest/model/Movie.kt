package com.tinnovakovic.springboot.fluttermovierest.model

import javax.persistence.*

@Entity
@Table(
    name = "movie",
    uniqueConstraints = [UniqueConstraint(name = "unique_movie_constraints", columnNames = ["movie_id"])]
)
data class Movie(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Int,
    @Column(name = "movie_id", nullable = false, columnDefinition = "TEXT")
    val movieId: String,
    @Column(name = "poster_path", nullable = false, columnDefinition = "TEXT")
    val posterPath: String,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "movie_detail_id", referencedColumnName = "id")
    var movieDetail: MovieDetail,

    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH]
    )
    @JoinTable(
        name = "appUser_movie",
        joinColumns = [JoinColumn(name = "movie_id")],
        inverseJoinColumns = [JoinColumn(name = "appUser_id")]
    )
    val appUsers: Set<AppUser>
)
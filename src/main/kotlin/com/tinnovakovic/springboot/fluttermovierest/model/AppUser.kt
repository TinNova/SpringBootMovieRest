package com.tinnovakovic.springboot.fluttermovierest.model

import javax.persistence.*

@Entity
@Table(
    name = "app_user",
    uniqueConstraints = [
        UniqueConstraint(name = "unique_appUser_constraints", columnNames = ["username", "email"])
    ]
)
data class AppUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    val id: Int,
    @Column(name = "username", nullable = false, columnDefinition = "TEXT")
    val username: String,
    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    val email: String,

    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH]
    )
    @JoinTable(
        name = "appUser_movie",
        joinColumns = [JoinColumn(name = "appUser_id")],
        inverseJoinColumns = [JoinColumn(name = "movie_id")]
    )
    val movies: Set<Movie> // convienence method? Do we need that?
)
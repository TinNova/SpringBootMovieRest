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
    @Column(name = "id", nullable = false, updatable = false)
    val id: Int,
    @Column(name = "username", nullable = false, columnDefinition = "TEXT")
    val username: String,
    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    val email: String,
    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    var password: String,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "app_user_detail_id", referencedColumnName = "id")
    val appUserDetail: AppUserDetail,
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH]
    )
    @JoinTable(
        name = "appUser_movie",
        joinColumns = [JoinColumn(name = "appUser_id")],
        inverseJoinColumns = [JoinColumn(name = "movie_id")]
    )
    val favMovies: Set<Movie>,
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH]
    )
    @JoinTable(
        name = "appUser_actor",
        joinColumns = [JoinColumn(name = "appUser_id")],
        inverseJoinColumns = [JoinColumn(name = "actor_id")]
    )
    val favActors: Set<Actor>,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "appUser_role",
        joinColumns = [JoinColumn(name = "appUser_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: Set<Role>
)
package com.tinnovakovic.springboot.fluttermovierest.model

import javax.persistence.*

@Entity
@Table(
    name = "actor",
    uniqueConstraints = [UniqueConstraint(name = "unique_actor_constraints", columnNames = ["actorMdb_id"])]
)
data class Actor (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Int,
    @Column(name = "actorMdb_id", nullable = false, columnDefinition = "TEXT")
    val actorMdbId: String,
    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    val name: String, // GetDetail
    @Column(name = "profile_path", nullable = false, columnDefinition = "TEXT")
    val profilePath: String, // GetDetail
    @Column(name = "biography", nullable = false, columnDefinition = "TEXT")
    val biography: String, // GetDetail
    @ManyToMany (mappedBy = "actors")
    val movieDetails: Set<MovieDetail>
//    val images: List<String>, // GetImages
)
package com.tinnovakovic.springboot.fluttermovierest.model

import javax.persistence.*

@Entity
@Table(
    name = "app_user_detail",
    uniqueConstraints = [
        UniqueConstraint(name = "unique_appUserDetail_constraints", columnNames = ["username", "email"])
    ]
)
data class AppUserDetail (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    val id: Int,
    @Column(name = "username", nullable = false, columnDefinition = "TEXT")
    val username: String,
    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    val email: String,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "app_user_detail_id")
    val reviews: Set<Review>
)
//    val favActors: List<Int>
//    val favDirectors: List<Int>
package com.tinnovakovic.springboot.fluttermovierest.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "review")
data class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Int,
    @Column(name = "comment", nullable = false, columnDefinition = "TEXT", length = 2000)
    val comment: String,
    @Column(name = "rating", nullable = false, scale=1)
    val rating: BigDecimal
)
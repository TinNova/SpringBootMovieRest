package com.tinnovakovic.springboot.fluttermovierest.rest_models

import java.math.BigDecimal

data class RestReview(
    val id: Int,
    val comment: String,
    val rating: BigDecimal
)
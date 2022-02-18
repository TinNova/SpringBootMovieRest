package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestReview


interface ReviewService {

    fun createReview(userDetailId: Int, movieDetailId: Int, restReview: RestReview): RestReview
    fun deleteReview(reviewId: Int): Unit
    fun updateReview(restReview: RestReview): RestReview
}
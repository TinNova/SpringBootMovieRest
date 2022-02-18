package com.tinnovakovic.springboot.fluttermovierest.service

import com.tinnovakovic.springboot.fluttermovierest.model.Review
import com.tinnovakovic.springboot.fluttermovierest.repo.AppUserDetailRepo
import com.tinnovakovic.springboot.fluttermovierest.repo.MovieDetailRepo
import com.tinnovakovic.springboot.fluttermovierest.repo.ReviewRepo
import com.tinnovakovic.springboot.fluttermovierest.rest_models.RestReview
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.util.*

@Service
class ReviewServiceImpl(
    private val reviewRepo: ReviewRepo,
    private val movieDetailRepo: MovieDetailRepo,
    private val userDetailRepo: AppUserDetailRepo
) : ReviewService {

    override fun createReview(userDetailId: Int, movieDetailId: Int, restReview: RestReview): RestReview {

        if (reviewRepo.findByUserDetailIdAndMovieDetailId(userDetailId, movieDetailId).isEmpty) {

            val userDetailEntity = userDetailRepo.findById(userDetailId)
            val movieDetailEntity = movieDetailRepo.findById(movieDetailId)

            if (movieDetailEntity.isPresent && movieDetailEntity.isPresent) {

                val entityReview = reviewRepo.save(
                    Review(
                        id = restReview.id,
                        comment = restReview.comment,
                        rating = restReview.rating
                    )
                )

                movieDetailRepo.save(
                    movieDetailEntity.get().copy(reviews = movieDetailEntity.get().reviews.plus(entityReview))
                )
                userDetailRepo.save(
                    userDetailEntity.get().copy(reviews = userDetailEntity.get().reviews.plus(entityReview))
                )
                return restReview.copy(id = entityReview.id)

            } else {
                if (movieDetailEntity.isEmpty && userDetailEntity.isEmpty) {
                    throw NoSuchElementException("A MovieDetail with an Id $movieDetailId and a UserDetail with an Id $userDetailId don't exist.")
                } else if (movieDetailEntity.isEmpty && userDetailEntity.isPresent) {
                    throw NoSuchElementException("A MovieDetail with an Id $movieDetailId doesn't exist.")
                } else {
                    throw NoSuchElementException("A UserDetail with an Id $userDetailId doesn't exist.")
                }
            }
        } else {
            throw IllegalArgumentException("A review for MovieDetail with id $movieDetailId by userDetail with id $userDetailId already exists")
        }
    }

    override fun deleteReview(reviewId: Int) {
        if (reviewRepo.findById(reviewId).isPresent) {
            reviewRepo.deleteById(reviewId)
        } else {
            throw NoSuchElementException("Could not find a review with an 'id' of ${reviewId}.")
        }
    }

    override fun updateReview(restReview: RestReview): RestReview {
        reviewRepo.findById(restReview.id).let {
            return if (it.isPresent) {
                reviewRepo.save(it.get().copy(comment = restReview.comment, rating = restReview.rating))
                restReview
            } else {
                throw NoSuchElementException("Could not find a review with an 'id' of ${restReview.id}.")
            }
        }
    }

    override fun findByIds(ids: List<Int>): List<RestReview> {
        return reviewRepo.findAllById(ids).map { RestReview(id = it.id, comment = it.comment, rating = it.rating) }
    }
}
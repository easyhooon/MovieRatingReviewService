package kr.ac.konkuk.movieratingreviewservice.data.api

import kr.ac.konkuk.movieratingreviewservice.domain.model.Review

interface ReviewApi {

    suspend fun getLatestReview(movieId: String): Review?

    suspend fun getAllReviews(movieId: String): List<Review>
}
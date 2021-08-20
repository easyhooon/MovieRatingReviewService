package kr.ac.konkuk.movieratingreviewservice.data.repository

import kr.ac.konkuk.movieratingreviewservice.domain.model.Review

interface ReviewRepository {

    suspend fun getLatestReview(movieId: String): Review?
}
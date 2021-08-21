package kr.ac.konkuk.movieratingreviewservice.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kr.ac.konkuk.movieratingreviewservice.data.api.ReviewApi
import kr.ac.konkuk.movieratingreviewservice.domain.model.Review

class ReviewRepositoryImplementation(
    private val reviewApi: ReviewApi,
    private val dispatchers: CoroutineDispatcher
) : ReviewRepository {

    override suspend fun getLatestReview(movieId: String): Review? = withContext(dispatchers) {
        reviewApi.getLatestReview(movieId)
    }

    override suspend fun getAllReviews(movieId: String): List<Review> = withContext(dispatchers) {
        reviewApi.getAllReviews(movieId)
    }
}
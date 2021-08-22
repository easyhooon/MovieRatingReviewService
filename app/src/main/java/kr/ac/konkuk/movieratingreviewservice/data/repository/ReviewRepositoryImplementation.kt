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

    override suspend fun getAllMovieReviews(movieId: String): List<Review> =
        withContext(dispatchers) {
            reviewApi.getAllMovieReviews(movieId)
        }

    override suspend fun getAllUserReviews(userId: String): List<Review> =
        withContext(dispatchers) {
            reviewApi.getAllUserReviews(userId)
        }

    override suspend fun addReview(review: Review): Review = withContext(dispatchers) {
        reviewApi.addReview(review)
        //성공했을 때 UI 를 갱신해주기 위해서 리뷰를 반환
    }

    override suspend fun removeReview(review: Review) = withContext(dispatchers) {
        reviewApi.removeReview(review)
    }
}
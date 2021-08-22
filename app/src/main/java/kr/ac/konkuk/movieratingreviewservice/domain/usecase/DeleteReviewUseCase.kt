package kr.ac.konkuk.movieratingreviewservice.domain.usecase


import kr.ac.konkuk.movieratingreviewservice.data.repository.ReviewRepository
import kr.ac.konkuk.movieratingreviewservice.domain.model.Review

class DeleteReviewUseCase(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(review: Review) =
        reviewRepository.removeReview(review)
}
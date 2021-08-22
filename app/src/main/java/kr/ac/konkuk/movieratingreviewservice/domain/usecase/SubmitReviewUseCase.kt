package kr.ac.konkuk.movieratingreviewservice.domain.usecase

import kr.ac.konkuk.movieratingreviewservice.data.repository.ReviewRepository
import kr.ac.konkuk.movieratingreviewservice.data.repository.UserRepository
import kr.ac.konkuk.movieratingreviewservice.domain.model.Movie
import kr.ac.konkuk.movieratingreviewservice.domain.model.Review
import kr.ac.konkuk.movieratingreviewservice.domain.model.User

class SubmitReviewUseCase(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke(
        movie: Movie,
        content: String,
        score: Float
    ): Review {
        var user = userRepository.getUser()

        if (user == null) {
            userRepository.saveUser(User())
            user = userRepository.getUser()
        }

        return reviewRepository.addReview(
            Review(
                userId = user!!.id,
                movieId = movie.id,
                content = content,
                score = score
            )
        )
    }
}
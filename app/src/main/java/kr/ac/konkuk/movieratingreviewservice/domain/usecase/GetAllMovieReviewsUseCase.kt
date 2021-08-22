package kr.ac.konkuk.movieratingreviewservice.domain.usecase

import kr.ac.konkuk.movieratingreviewservice.data.repository.ReviewRepository
import kr.ac.konkuk.movieratingreviewservice.data.repository.UserRepository
import kr.ac.konkuk.movieratingreviewservice.domain.model.MovieReviews
import kr.ac.konkuk.movieratingreviewservice.domain.model.Review
import kr.ac.konkuk.movieratingreviewservice.domain.model.User

class GetAllMovieReviewsUseCase(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) {
    //pair 를 사용해서 <Review, List<Review>> 를 반환하게 해도 되긴 하지만
    //여러 데이터를 묶기는 좋음 Triple 도 있음
    //다만 안의 요소에 대한 접근을 first 와 second 로 하기 때문에 실제로 들어가는 값이 무엇인지 이름으론 불명확함 
    suspend operator fun invoke(movieId: String): MovieReviews {
        val reviews = reviewRepository.getAllMovieReviews(movieId)
        val user = userRepository.getUser()

        if (user == null) {
            userRepository.saveUser(User())

            return MovieReviews(null, reviews)
        }

        return MovieReviews(
            reviews.find { it.userId == user.id },
            reviews.filter { it.userId != user.id }
        )
    }


}
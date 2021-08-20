package kr.ac.konkuk.movieratingreviewservice.domain.usecase

import kr.ac.konkuk.movieratingreviewservice.data.repository.MovieRepository
import kr.ac.konkuk.movieratingreviewservice.data.repository.ReviewRepository
import kr.ac.konkuk.movieratingreviewservice.domain.model.FeaturedMovie

class GetRandomFeaturedMovieUseCase(
    private val movieRepository: MovieRepository,
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke(): FeaturedMovie? {
        val featuredMovies = movieRepository.getAllMovies()
                //Id 가 null 인 것을 걸러냄
            .filter { it.id.isNullOrBlank().not() }
                // 추천 영화만 걸러냄
            .filter { it.isFeatured == true }

        if (featuredMovies.isNullOrEmpty()) {
            return null
        }

        //하나를 랜덤으로 가져옴
        return featuredMovies.random()
            .let { movie ->
                val latestReview = reviewRepository.getLatestReview(movie.id!!)
                FeaturedMovie(movie, latestReview)
            }
    }
}
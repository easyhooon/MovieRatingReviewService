package kr.ac.konkuk.movieratingreviewservice.domain.usecase

import kr.ac.konkuk.movieratingreviewservice.data.repository.MovieRepository
import kr.ac.konkuk.movieratingreviewservice.domain.model.Movie

class GetAllMoviesUseCase(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(): List<Movie> = movieRepository.getAllMovies()
}
package kr.ac.konkuk.movieratingreviewservice.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kr.ac.konkuk.movieratingreviewservice.data.api.MovieApi
import kr.ac.konkuk.movieratingreviewservice.domain.model.Movie

//dispatchers 를 주입받아 메소드를 구현
class MovieRepositoryImplementation(
    private val movieApi: MovieApi,
    private val dispatchers: CoroutineDispatcher
) : MovieRepository {

    override suspend fun getAllMovies(): List<Movie> = withContext(dispatchers) {
        movieApi.getAllMovies()
    }
}
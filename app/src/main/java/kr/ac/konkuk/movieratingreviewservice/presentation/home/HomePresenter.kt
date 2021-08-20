package kr.ac.konkuk.movieratingreviewservice.presentation.home

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kr.ac.konkuk.movieratingreviewservice.domain.usecase.GetAllMoviesUseCase
import kr.ac.konkuk.movieratingreviewservice.domain.usecase.GetRandomFeaturedMovieUseCase
import java.lang.Exception

class HomePresenter(
    private val view: HomeContract.View,
    private val getRandomFeaturedMovie: GetRandomFeaturedMovieUseCase,
    private val getAllMovies: GetAllMoviesUseCase
) : HomeContract.Presenter {

    override val scope: CoroutineScope = MainScope()

    override fun onViewCreated() {
        fetchMovies()
    }

    override fun onDestroyView() {}

    private fun fetchMovies() = scope.launch {
        try {
            //indicator 를 보여줌
            view.showLoadingIndicator()
            val featuredMovie = getRandomFeaturedMovie()
            val movies = getAllMovies()
            view.showMovies(featuredMovie, movies)
        } catch (e: Exception) {
            e.printStackTrace()
            view.showErrorDescription("에러가 발생했어요 😥")
        } finally {
            //loading indicator 를 숨김
            view.hideLoadingIndicator()
        }
    }

}
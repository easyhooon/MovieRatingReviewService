package kr.ac.konkuk.movieratingreviewservice.presentation.home

import kr.ac.konkuk.movieratingreviewservice.domain.model.FeaturedMovie
import kr.ac.konkuk.movieratingreviewservice.domain.model.Movie
import kr.ac.konkuk.movieratingreviewservice.presentation.BasePresenter
import kr.ac.konkuk.movieratingreviewservice.presentation.BaseView

interface HomeContract {

    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showErrorDescription(message: String)

        fun showMovies(
            featuredMovie: FeaturedMovie?,
            movies: List<Movie>
        )
    }

    interface Presenter : BasePresenter
}
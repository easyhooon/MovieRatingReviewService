package kr.ac.konkuk.movieratingreviewservice.presentation.mypage

import kr.ac.konkuk.movieratingreviewservice.domain.model.ReviewedMovie
import kr.ac.konkuk.movieratingreviewservice.presentation.BasePresenter
import kr.ac.konkuk.movieratingreviewservice.presentation.BaseView


interface MyPageContract {

    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showNoDataDescription(message: String)

        fun showErrorDescription(message: String)

        fun showReviewedMovies(reviewedMovies: List<ReviewedMovie>)
    }

    interface Presenter : BasePresenter
}
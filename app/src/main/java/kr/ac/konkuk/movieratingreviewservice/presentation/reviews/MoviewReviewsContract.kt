package kr.ac.konkuk.movieratingreviewservice.presentation.reviews

import kr.ac.konkuk.movieratingreviewservice.domain.model.Movie
import kr.ac.konkuk.movieratingreviewservice.domain.model.MovieReviews
import kr.ac.konkuk.movieratingreviewservice.domain.model.Review
import kr.ac.konkuk.movieratingreviewservice.presentation.BasePresenter
import kr.ac.konkuk.movieratingreviewservice.presentation.BaseView

interface MovieReviewsContract {

    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showErrorDescription(message: String)

        fun showMovieInformation(movie: Movie)

        fun showReviews(reviews: MovieReviews)

        fun showErrorToast(message: String)
    }

    interface Presenter : BasePresenter {

        val movie: Movie

        fun requestAddReview(content: String, score: Float)

        fun requestRemoveReview(review: Review)
    }
}
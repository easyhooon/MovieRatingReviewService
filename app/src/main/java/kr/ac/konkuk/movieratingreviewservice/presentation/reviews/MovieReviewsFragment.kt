package kr.ac.konkuk.movieratingreviewservice.presentation.reviews

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.konkuk.movieratingreviewservice.databinding.FragmentMovieReviewsBinding
import kr.ac.konkuk.movieratingreviewservice.domain.model.Movie
import kr.ac.konkuk.movieratingreviewservice.domain.model.MovieReviews
import kr.ac.konkuk.movieratingreviewservice.domain.model.Review
import org.koin.android.scope.ScopeFragment
import org.koin.core.parameter.parametersOf

class MovieReviewsFragment : ScopeFragment(), MovieReviewsContract.View {

    override val presenter: MovieReviewsContract.Presenter by inject { parametersOf(arguments.movie) }

    //safeArgument 사용 위해
    private val arguments: MovieReviewsFragmentArgs by navArgs()
    private var binding: FragmentMovieReviewsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMovieReviewsBinding.inflate(inflater, container, false)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.onViewCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showLoadingIndicator() {
        binding?.progressBar?.isVisible = true
    }

    override fun hideLoadingIndicator() {
        binding?.progressBar?.isVisible = false
    }

    override fun showErrorDescription(message: String) {
        binding?.recyclerView?.isVisible = false
        binding?.errorDescriptionTextView?.isVisible = true
        binding?.errorDescriptionTextView?.text = message
    }

    override fun showMovieInformation(movie: Movie) {
        //여기까지 도달해야 adapter 를 선언 및 장착
        binding?.recyclerView?.adapter = MovieReviewsAdapter(movie).apply {
            onReviewSubmitButtonClickListener = { content, score ->
                presenter.requestAddReview(content, score)
                hideKeyboard()
            }
            onReviewDeleteButtonClickListener = { review ->
                showDeleteConfirmDialog(review)
            }
        }
    }

    private fun showDeleteConfirmDialog(review: Review) {
        AlertDialog.Builder(requireContext())
            .setMessage("정말로 리뷰를 삭제하시겠어요?")
            .setPositiveButton("삭제할래요") { _, _ ->
                presenter.requestRemoveReview(review)
            }
            .setNegativeButton("안할래요") { _, _ -> }
            .show()
    }

    override fun showReviews(reviews: MovieReviews) {
        binding?.recyclerView?.isVisible = true
        binding?.errorDescriptionTextView?.isVisible = false
        (binding?.recyclerView?.adapter as? MovieReviewsAdapter)?.apply {
            this.myReview = reviews.myReview
            this.reviews = reviews.othersReview
            //ui 갱신을 요청
            notifyDataSetChanged()
        }
    }

    override fun showErrorToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun initViews() {
        //adapter 넣어주지 않음
        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(
                this.context,
                RecyclerView.VERTICAL,
                false
            )
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }
}
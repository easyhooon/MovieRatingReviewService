package kr.ac.konkuk.movieratingreviewservice.presentation.reviews

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import kr.ac.konkuk.movieratingreviewservice.databinding.ItemMovieInformationBinding
import kr.ac.konkuk.movieratingreviewservice.databinding.ItemMyReviewBinding
import kr.ac.konkuk.movieratingreviewservice.databinding.ItemReviewBinding
import kr.ac.konkuk.movieratingreviewservice.databinding.ItemReviewFormBinding
import kr.ac.konkuk.movieratingreviewservice.domain.model.Movie
import kr.ac.konkuk.movieratingreviewservice.domain.model.Review
import kr.ac.konkuk.movieratingreviewservice.extension.toAbbreviatedString
import kr.ac.konkuk.movieratingreviewservice.extension.toDecimalFormatString

class MovieReviewsAdapter(private val movie: Movie) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var myReview: Review? = null
    var reviews: List<Review> = emptyList()

    var onReviewSubmitButtonClickListener: ((content: String, score: Float) -> Unit)? = null
    var onReviewDeleteButtonClickListener: ((Review) -> Unit)? = null

    //데이터 내에 포지션만 보정하는 방식 사용, 데이터를 따로 조립 x
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> {
                MovieInformationViewHolder(
                    ItemMovieInformationBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            ITEM_VIEW_TYPE_ITEM -> {
                ReviewViewHolder(parent)
            }

            ITEM_VIEW_TYPE_REVIEW_FORM -> {
                ReviewFormViewHolder(
                    ItemReviewFormBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            ITEM_VIEW_TYPE_MY_REVIEW -> {
                MyReviewViewHolder(
                    ItemMyReviewBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            else -> throw RuntimeException("알 수 없는 ViewType 입니다.")
        }

    //헤더의 숫자를 따로 count
    //항상 두 개의 헤더가 리뷰 이전에 존재
    override fun getItemCount(): Int = 2 + reviews.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieInformationViewHolder -> {
                holder.bind(movie)
            }
            is ReviewViewHolder -> {
                //헤더의 개수만큼 -
                //안해주면 index 관련 crash 발생
                holder.bind(reviews[position - 2])
            }
            is MyReviewViewHolder -> {
                myReview ?: return
                holder.bind(myReview!!)
            }
            is ReviewFormViewHolder -> Unit

            else -> throw RuntimeException("알 수 없는 ViewHolder 입니다.")
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> ITEM_VIEW_TYPE_HEADER
            1 -> {
                if (myReview == null) {
                    //내가 쓴 리뷰가 없으면 리뷰 폼을 띄움
                    ITEM_VIEW_TYPE_REVIEW_FORM
                } else {
                    ITEM_VIEW_TYPE_MY_REVIEW
                }
            }
            else -> ITEM_VIEW_TYPE_ITEM
        }

    class MovieInformationViewHolder(private val binding: ItemMovieInformationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Movie) {
            Glide.with(binding.root)
                .load(item.posterUrl)
                .into(binding.posterImageView)

            item.let {
                binding.averageScoreTextView.text =
                    "평점 ${it.averageScore?.toDecimalFormatString("0.0")} (${it.numberOfScore?.toAbbreviatedString()})"
                binding.titleTextView.text = it.title
                binding.additionalInformationTextView.text = "${it.releaseYear}·${it.country}"
                binding.relationsTextView.text = "감독: ${it.director}\n출연진: ${it.actors}"
                binding.genreChipGroup.removeAllViews()
                // #~~ #~~ 형태이기 때문에 띄어쓰기 로 분리 후 가져옴
                it.genre?.split(" ")?.forEach { genre ->
                    binding.genreChipGroup.addView(
                        Chip(binding.root.context).apply {
                            isClickable = false
                            text = genre
                        }
                    )
                }
            }
        }
    }

    inner class ReviewViewHolder(
        parent: ViewGroup,
        private val binding: ItemReviewBinding = ItemReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Review) {
            item.let {
                binding.authorIdTextView.text = "${it.userId?.take(3)}***"
                binding.scoreTextView.text = it.score?.toDecimalFormatString("0.0")
                binding.contentsTextView.text = "\"${it.content}\""
            }
        }
    }

    @SuppressLint("SetTextI18n")
    inner class ReviewFormViewHolder(private val binding: ItemReviewFormBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.submitButton.setOnClickListener {
                onReviewSubmitButtonClickListener?.invoke(
                    binding.reviewFieldEditText.text.toString(),
                    binding.ratingBar.rating
                )
            }
            binding.reviewFieldEditText.addTextChangedListener { editable ->
                binding.contentLimitTextView.text = "(${editable?.length ?: 0}/50)"
                //작성한 글자가 5개 이상이어야 submit 버튼 활성화
                binding.submitButton.isEnabled = (editable?.length ?: 0) >= 5
            }
        }
    }

    inner class MyReviewViewHolder(private val binding: ItemMyReviewBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.deleteButton.setOnClickListener {
                onReviewDeleteButtonClickListener?.invoke(myReview!!)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: Review) {
            item.let {
                binding.scoreTextView.text = it.score?.toDecimalFormatString("0.0")
                binding.contentsTextView.text = "\"${it.content}\""
            }
        }
    }


    companion object {
        const val ITEM_VIEW_TYPE_HEADER = 0
        const val ITEM_VIEW_TYPE_ITEM = 1
        const val ITEM_VIEW_TYPE_REVIEW_FORM = 2
        const val ITEM_VIEW_TYPE_MY_REVIEW = 3
    }
}
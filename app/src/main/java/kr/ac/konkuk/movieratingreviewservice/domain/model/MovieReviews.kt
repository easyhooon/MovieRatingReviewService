package kr.ac.konkuk.movieratingreviewservice.domain.model

//내가 쓴 리뷰를 최상단에 올리기 위해 내 리뷰와 타인의 리뷰를 구분
data class MovieReviews(
    val myReview: Review?,
    val othersReview: List<Review>
)

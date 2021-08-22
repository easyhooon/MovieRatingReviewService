package kr.ac.konkuk.movieratingreviewservice.domain.model

data class ReviewedMovie(
    val movie: Movie,
    val myReview: Review
)
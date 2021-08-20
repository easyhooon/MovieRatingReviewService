package kr.ac.konkuk.movieratingreviewservice.domain.model

data class FeaturedMovie(
    val movie: Movie,
    val latestReview: Review?
)
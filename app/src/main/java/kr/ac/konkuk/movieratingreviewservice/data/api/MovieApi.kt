package kr.ac.konkuk.movieratingreviewservice.data.api

import kr.ac.konkuk.movieratingreviewservice.domain.model.Movie

interface MovieApi {

    suspend fun getAllMovies(): List<Movie>
}
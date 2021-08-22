package kr.ac.konkuk.movieratingreviewservice.data.repository

import kr.ac.konkuk.movieratingreviewservice.domain.model.Movie

interface MovieRepository {

    suspend fun getAllMovies(): List<Movie>

    suspend fun getMovies(movieIds: List<String>): List<Movie>
}
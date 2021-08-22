package kr.ac.konkuk.movieratingreviewservice.data.repository

import kr.ac.konkuk.movieratingreviewservice.domain.model.User


interface UserRepository {

    suspend fun getUser(): User?

    suspend fun saveUser(user: User)
}
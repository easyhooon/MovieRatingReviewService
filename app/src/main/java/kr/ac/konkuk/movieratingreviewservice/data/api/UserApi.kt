package kr.ac.konkuk.movieratingreviewservice.data.api

import kr.ac.konkuk.movieratingreviewservice.domain.model.User


interface UserApi {

    suspend fun saveUser(user: User): User
}
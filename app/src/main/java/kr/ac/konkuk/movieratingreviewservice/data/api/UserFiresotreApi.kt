package kr.ac.konkuk.movieratingreviewservice.data.api

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kr.ac.konkuk.movieratingreviewservice.domain.model.User

class UserFirestoreApi(
    private val firestore: FirebaseFirestore
) : UserApi {

    override suspend fun saveUser(user: User): User =
        firestore.collection("users")
            //set 을 사용하려면 정확하게 Id 를 명시해야함
            .add(user)
            .await()
            .let { User(it.id) }

}
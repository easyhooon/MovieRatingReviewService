package kr.ac.konkuk.movieratingreviewservice.data.api

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import kr.ac.konkuk.movieratingreviewservice.domain.model.Review

class ReviewFirestoreApi(
    private val firestore: FirebaseFirestore
) : ReviewApi {

    //Firebase Query
    //복합 색인 문제 해결 필요 
    override suspend fun getLatestReview(movieId: String): Review? =
        firestore.collection("reviews")
            .whereEqualTo("movieId", movieId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()
            .map { it.toObject<Review>() }
            .firstOrNull()
}
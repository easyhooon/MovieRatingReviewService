package kr.ac.konkuk.movieratingreviewservice.data.api

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import kr.ac.konkuk.movieratingreviewservice.domain.model.Movie
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

    //집계를 클라이언트에서 모드 해주는 방식으로 구현

    //위에서 이미 복합 색인(composite indexing)을 신청했기때문에 문제 없음
    override suspend fun getAllMovieReviews(movieId: String): List<Review> =
        firestore.collection("reviews")
            .whereEqualTo("movieId", movieId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .await()
            .map { it.toObject<Review>() }

    override suspend fun getAllUserReviews(userId: String): List<Review> =
        firestore.collection("reviews")
            .whereEqualTo("userId", userId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .await()
            .map { it.toObject<Review>() }

    override suspend fun addReview(review: Review): Review {
        val newReviewReference = firestore.collection("reviews").document()
        val movieReference = firestore.collection("movies").document(review.movieId!!)

        firestore.runTransaction { transaction ->
            val movie = transaction.get(movieReference).toObject<Movie>()!!

            //리뷰를 작성하엿을 때, 삭제하였을 때 평균 점수와 평가 갯수는 같이 변경되어야 함
            //하나의 트랜젝션으로 묶어서 실패를 할 경우 다 같이 실패하도록 코드 작성
            val oldAverageScore = movie.averageScore ?: 0f
            val oldNumberOfScore = movie.numberOfScore ?: 0
            val oldTotalScore = oldAverageScore * oldNumberOfScore

            val newNumberOfScore = oldNumberOfScore + 1
            val newAverageScore = (oldTotalScore + (review.score ?: 0f)) / newNumberOfScore

            transaction.set(
                movieReference,
                movie.copy(
                    numberOfScore = newNumberOfScore,
                    averageScore = newAverageScore
                )
            )

            transaction.set(
                newReviewReference,
                review,
                SetOptions.merge()
            )
        }.await()

        return newReviewReference.get().await().toObject<Review>()!!
    }

    override suspend fun removeReview(review: Review) {
        val reviewReference = firestore.collection("reviews").document(review.id!!)
        val movieReference = firestore.collection("movies").document(review.movieId!!)

        firestore.runTransaction { transaction ->
            val movie = transaction
                .get(movieReference)
                .toObject<Movie>()!!

            //리뷰를 작성하엿을 때, 삭제하였을 때 평균 점수와 평가 갯수는 같이 변경되어야 함
            //하나의 트랜젝션으로 묶어서 실패를 할 경우 다 같이 실패하도록 코드 작성
            val oldAverageScore = movie.averageScore ?: 0f
            val oldNumberOfScore = movie.numberOfScore ?: 0
            val oldTotalScore = oldAverageScore * oldNumberOfScore

            //최소값이 0 아래로 떨어지지 않게 
            val newNumberOfScore = (oldNumberOfScore - 1).coerceAtLeast(0)
            val newAverageScore = if (newNumberOfScore > 0) {
                (oldTotalScore - (review.score ?: 0f)) / newNumberOfScore
            } else {
                0f
            }

            transaction.set(
                movieReference,
                movie.copy(
                    numberOfScore = newNumberOfScore,
                    averageScore = newAverageScore
                )
            )

            transaction.delete(reviewReference)
        }.await()
    }
}
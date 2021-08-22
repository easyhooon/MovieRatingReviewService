package kr.ac.konkuk.movieratingreviewservice.data.api

import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.ktx.toObject

import kr.ac.konkuk.movieratingreviewservice.domain.model.Movie

//firestoreApi 를 주입을 받음
class MovieFirestoreApi(
    //인스턴스를 가져옴
    private val firestore: FirebaseFirestore
) : MovieApi {

    override suspend fun getAllMovies(): List<Movie> =
        firestore.collection("movies")
//            .get()
//            .addOnCompleteListener {  }
//            .map { it.toObject<Movie>() }


            .get()
            .await() //coroutines-play-services 를 의존성 추가 해야지만 사용 가능
            .map { it.toObject<Movie>() } //가져온 데이터는 querySnapshot 이지만 Movie 타입으로 변환 해줌

    override suspend fun getMovies(movieIds: List<String>): List<Movie> =
        firestore.collection("movies")
            //필드에 해당하는 이름이 list 에 속하는지, movieIds <-- list
            .whereIn(FieldPath.documentId(), movieIds)
            .get()
            .await()
            .map { it.toObject<Movie>() }
}
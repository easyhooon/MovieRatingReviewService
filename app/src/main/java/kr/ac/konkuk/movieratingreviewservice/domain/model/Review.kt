package kr.ac.konkuk.movieratingreviewservice.domain.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Review(
    @DocumentId
    val id: String? = null,

    //추가를 하게 되면 자동으로 serverTimeStamp 가 찍히게 됨, 내려받을 때 자동으로 Date 타입으로 변환 해줌
    @ServerTimestamp
    val createdAt: Date? = null,

    val userId: String? = null,
    val movieId: String? = null,
    val content: String? = null,
    val score: Float? = null
)
package kr.ac.konkuk.movieratingreviewservice.domain.model

import com.google.firebase.firestore.DocumentId

data class Movie(
    //id 는 직접지정할 수 있고, 유니크한 Id 로 자동생성 가능
    //field 값은 아니지만 annotation 을 달아서 toObject 를 하면 id 값이 property 로 들어옴
    @DocumentId
    val id: String? = null,

    //boolean 의 name 이 isFeatured 같은 형식이면 @field:JvmField 를 annotation 을 붙히는 것을 권장
    @field:JvmField
    val isFeatured: Boolean? = null,

    val title: String? = null,
    val actors: String? = null,
    val country: String? = null,
    val director: String? = null,
    val genre: String? = null,
    val posterUrl: String? = null,
    val rating: String? = null,
    val averageScore: Float? = null,
    val numberOfScore: Int? = null,
    val releaseYear: Int? = null,
    val runtime: Int? = null
)

// 모든 type 들이 nullable 한 이유:
// 파이어스토어에서 사용되는 커스텀 객체는 default constructor 가 제공이 되야 함 ( ex) Movie() )
// 데이터가 들어오지 않을 경우에 대한 예외처리를 위해 nullable 선언

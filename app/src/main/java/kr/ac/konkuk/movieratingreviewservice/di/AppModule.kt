package kr.ac.konkuk.movieratingreviewservice.di

import android.app.Activity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kr.ac.konkuk.movieratingreviewservice.data.api.*
import kr.ac.konkuk.movieratingreviewservice.data.preference.PreferenceManager
import kr.ac.konkuk.movieratingreviewservice.data.preference.SharedPreferenceManager
import kr.ac.konkuk.movieratingreviewservice.data.repository.*
import kr.ac.konkuk.movieratingreviewservice.domain.model.Movie
import kr.ac.konkuk.movieratingreviewservice.domain.usecase.*
import kr.ac.konkuk.movieratingreviewservice.presentation.home.HomeContract
import kr.ac.konkuk.movieratingreviewservice.presentation.home.HomeFragment
import kr.ac.konkuk.movieratingreviewservice.presentation.home.HomePresenter
import kr.ac.konkuk.movieratingreviewservice.presentation.mypage.MyPageContract
import kr.ac.konkuk.movieratingreviewservice.presentation.mypage.MyPageFragment
import kr.ac.konkuk.movieratingreviewservice.presentation.mypage.MyPagePresenter
import kr.ac.konkuk.movieratingreviewservice.presentation.reviews.MovieReviewsContract
import kr.ac.konkuk.movieratingreviewservice.presentation.reviews.MovieReviewsFragment
import kr.ac.konkuk.movieratingreviewservice.presentation.reviews.MovieReviewsPresenter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { Dispatchers.IO }
}

//usecase 들을 주입 받아서 활용
val dataModule = module {
    single { Firebase.firestore }

    //class 의 property 갯수 만큼 get()
    single<MovieApi> { MovieFirestoreApi(get()) }
    single<ReviewApi> { ReviewFirestoreApi(get()) }
    single<UserApi> { UserFirestoreApi(get()) }

    single<MovieRepository> { MovieRepositoryImplementation(get(), get()) }
    single<ReviewRepository> { ReviewRepositoryImplementation(get(), get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }

    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE) }
    single<PreferenceManager> { SharedPreferenceManager(get()) }
}

val domainModule = module {
    factory { GetRandomFeaturedMovieUseCase(get(), get()) }
    //movieRepository 를 주입 받음
    factory { GetAllMoviesUseCase(get()) }
    //reviewRepository 를 주입 받음
    factory { GetAllMovieReviewsUseCase(get(), get()) }
    factory { GetMyReviewedMoviesUseCase(get(), get(), get()) }
    factory { SubmitReviewUseCase(get(), get()) }
    factory { DeleteReviewUseCase(get()) }
}

val presenterModule = module {
    scope<HomeFragment> {
        scoped<HomeContract.Presenter> { HomePresenter(getSource(), get(), get()) }
    }
    scope<MovieReviewsFragment> {
        scoped<MovieReviewsContract.Presenter> { (movie: Movie) ->
            MovieReviewsPresenter(movie, getSource(), get(), get(), get())
        }
    }
    scope<MyPageFragment> {
        scoped<MyPageContract.Presenter> { MyPagePresenter(getSource(), get()) }
    }
}
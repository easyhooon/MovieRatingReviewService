package kr.ac.konkuk.movieratingreviewservice.di

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kr.ac.konkuk.movieratingreviewservice.data.api.MovieApi
import kr.ac.konkuk.movieratingreviewservice.data.api.MovieFirestoreApi
import kr.ac.konkuk.movieratingreviewservice.data.api.ReviewApi
import kr.ac.konkuk.movieratingreviewservice.data.api.ReviewFirestoreApi
import kr.ac.konkuk.movieratingreviewservice.data.repository.MovieRepository
import kr.ac.konkuk.movieratingreviewservice.data.repository.MovieRepositoryImplementation
import kr.ac.konkuk.movieratingreviewservice.data.repository.ReviewRepository
import kr.ac.konkuk.movieratingreviewservice.data.repository.ReviewRepositoryImplementation
import kr.ac.konkuk.movieratingreviewservice.domain.usecase.GetAllMoviesUseCase
import kr.ac.konkuk.movieratingreviewservice.domain.usecase.GetRandomFeaturedMovieUseCase
import kr.ac.konkuk.movieratingreviewservice.presentation.home.HomeContract
import kr.ac.konkuk.movieratingreviewservice.presentation.home.HomeFragment
import kr.ac.konkuk.movieratingreviewservice.presentation.home.HomePresenter
import org.koin.dsl.module

val appModule = module {
    single { Dispatchers.IO }
}

//usecase 들을 주입 받아서 활용
val dataModule = module {
    single { Firebase.firestore }

    single<MovieApi> { MovieFirestoreApi(get()) }
    single<ReviewApi> { ReviewFirestoreApi(get()) }

    single<MovieRepository> { MovieRepositoryImplementation(get(), get()) }
    single<ReviewRepository> { ReviewRepositoryImplementation(get(), get()) }
}

val domainModule = module {
    factory { GetRandomFeaturedMovieUseCase(get(), get()) }
    factory { GetAllMoviesUseCase(get()) }
}

val presenterModule = module {
    scope<HomeFragment> {
        scoped<HomeContract.Presenter> { HomePresenter(getSource(), get(), get())}
    }
}
package kr.ac.konkuk.movieratingreviewservice

import android.app.Application
import kr.ac.konkuk.movieratingreviewservice.di.appModule
import kr.ac.konkuk.movieratingreviewservice.di.dataModule
import kr.ac.konkuk.movieratingreviewservice.di.domainModule
import kr.ac.konkuk.movieratingreviewservice.di.presenterModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieReviewApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(
                if(BuildConfig.DEBUG) {
                    Level.DEBUG
                } else {
                    Level.NONE
                }
            )
            androidContext(this@MovieReviewApplication)
            modules(appModule + dataModule + domainModule + presenterModule)
        }

//        MovieDataGenerator().generate()
    }
}
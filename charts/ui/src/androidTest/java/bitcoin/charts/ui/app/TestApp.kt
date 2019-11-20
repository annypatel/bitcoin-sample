package bitcoin.charts.ui.app

import bitcoin.charts.ui.di.DaggerTestAppComponent
import bitcoin.test.ui.DaggerTestApplication
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Application class for charts UI module for running instrumentation tests.
 */
class TestApp : DaggerTestApplication() {

    override val component: AndroidInjector<out DaggerApplication> by lazy {
        DaggerTestAppComponent.create()
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}

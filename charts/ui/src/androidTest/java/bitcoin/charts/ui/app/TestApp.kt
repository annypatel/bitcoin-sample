package bitcoin.charts.ui.app

import bitcoin.base.ui.BaseApplication
import bitcoin.charts.ui.di.DaggerTestAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Application class for charts UI module for running instrumentation tests.
 */
class TestApp : BaseApplication() {

    override val component: AndroidInjector<out DaggerApplication> by lazy {
        DaggerTestAppComponent.create()
    }
}

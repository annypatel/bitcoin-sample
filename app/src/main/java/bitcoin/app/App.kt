package bitcoin.app

import bitcoin.app.di.DaggerAppComponent
import bitcoin.base.ui.BaseApplication
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Application class for this app. Use this class to do one time initialization.
 */
class App : BaseApplication() {

    override val component: AndroidInjector<out DaggerApplication> by lazy {
        DaggerAppComponent.create()
    }
}

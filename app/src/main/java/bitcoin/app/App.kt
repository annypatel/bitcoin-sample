package bitcoin.app

import bitcoin.app.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Application class for this app. Use this class to do one time initialization.
 */
class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<App> {
        return DaggerAppComponent.create()
    }
}

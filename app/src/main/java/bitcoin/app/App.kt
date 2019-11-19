package bitcoin.app

import bitcoin.app.di.DaggerAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Application class for this app. Use this class to do one time initialization.
 */
class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

    override fun applicationInjector(): AndroidInjector<App> {
        return DaggerAppComponent.create()
    }
}

package bitcoin.base.ui

import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Base application for doing common one-time initialization.
 */
abstract class BaseApplication : DaggerApplication() {

    abstract val component: AndroidInjector<out DaggerApplication>

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

    public override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return component
    }
}

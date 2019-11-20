package bitcoin.test.ui

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Base application class for instrumentation tests.
 */
abstract class DaggerTestApplication : DaggerApplication() {

    abstract val component: AndroidInjector<out DaggerApplication>

    public override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return component
    }
}

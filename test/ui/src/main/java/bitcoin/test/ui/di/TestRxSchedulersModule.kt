package bitcoin.test.ui.di

import bitcoin.base.domain.RxSchedulers
import com.squareup.rx2.idler.Rx2Idler
import dagger.Module
import dagger.Provides
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

/**
 * Module that wraps schedulers with idling resource using RxIdler before injecting [RxSchedulers]
 * into test application component.
 */
@Module
object TestRxSchedulersModule {

    @Provides
    @Singleton
    fun rxSchedulers(): RxSchedulers {
        RxJavaPlugins.setInitIoSchedulerHandler(Rx2Idler.create("io"))
        RxJavaPlugins.setInitComputationSchedulerHandler(Rx2Idler.create("computation"))
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(Rx2Idler.create("main"))
        return RxSchedulers(
            main = AndroidSchedulers.mainThread(),
            io = Schedulers.io(),
            computation = Schedulers.computation()
        )
    }
}

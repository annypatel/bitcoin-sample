package bitcoin.app.di

import bitcoin.base.domain.RxSchedulers
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

/**
 * Dagger module to declare [RxSchedulers] to be bind into application component.
 */
@Module
object RxSchedulersModule {

    @Provides
    @Singleton
    fun rxSchedulers() = RxSchedulers(
        main = AndroidSchedulers.mainThread(),
        io = Schedulers.io(),
        computation = Schedulers.computation()
    )
}

package bitcoin.test.ui.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okreplay.OkReplayInterceptor
import javax.inject.Singleton

/**
 * Dagger module to inject [OkReplayInterceptor] into application component.
 */
@Module
abstract class OkReplayInterceptorModule {

    @Binds
    @IntoSet
    abstract fun bindOkReplayInterceptor(interceptor: OkReplayInterceptor): Interceptor

    @Module
    companion object {

        @Provides
        @Singleton
        @JvmStatic
        fun okReplayInterceptor(): OkReplayInterceptor = OkReplayInterceptor()
    }
}

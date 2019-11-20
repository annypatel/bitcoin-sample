package bitcoin.test.ui.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okreplay.OkReplayInterceptor
import javax.inject.Singleton

/**
 * Dagger module for [OkReplayInterceptor].
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
        fun okReplayInterceptor(): OkReplayInterceptor {
            return OkReplayInterceptor()
        }
    }
}

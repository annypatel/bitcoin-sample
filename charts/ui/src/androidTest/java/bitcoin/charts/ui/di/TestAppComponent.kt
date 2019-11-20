package bitcoin.charts.ui.di

import bitcoin.base.data.di.NetworkModule
import bitcoin.base.ui.di.ViewModelFactoryModule
import bitcoin.charts.data.di.ChartsDataModule
import bitcoin.charts.domain.di.ChartsDomainModule
import bitcoin.charts.ui.app.TestApp
import bitcoin.test.ui.OkReplayInterceptorProvider
import bitcoin.test.ui.di.OkReplayInterceptorModule
import bitcoin.test.ui.di.TestRxSchedulersModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * The dagger component for the [TestApp] of charts UI module.
 */
@Singleton
@Component(
    modules = [
        TestRxSchedulersModule::class,
        OkReplayInterceptorModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class,
        NetworkModule::class,
        ChartsDomainModule::class,
        ChartsDataModule::class,
        ChartsUiModule::class
    ]
)
interface TestAppComponent : AndroidInjector<TestApp>, OkReplayInterceptorProvider {

    @Component.Factory
    interface Factory {

        fun create(): TestAppComponent
    }
}

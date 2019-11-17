package bitcoin.app.di

import bitcoin.app.App
import bitcoin.base.data.di.NetworkModule
import bitcoin.base.ui.di.ViewModelFactoryModule
import bitcoin.charts.data.di.ChartsDataModule
import bitcoin.charts.domain.di.ChartsDomainModule
import bitcoin.charts.ui.di.ChartsUiModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * The dagger component for the app, manages application level dependencies, annotated as
 * [Singleton] and [App] will ensure that only one instance of the class is created.
 */
@Singleton
@Component(
    modules = [
        RxSchedulersModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class,
        NetworkModule::class,
        ChartsDomainModule::class,
        ChartsDataModule::class,
        ChartsUiModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {

        fun create(): AppComponent
    }
}

package bitcoin.base.ui.di

import androidx.lifecycle.ViewModelProvider
import bitcoin.base.ui.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * Dagger module that declares [ViewModelProvider.Factory] to be bind into application component.
 */
@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

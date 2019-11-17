package bitcoin.charts.ui.di

import androidx.lifecycle.ViewModel
import bitcoin.base.ui.di.ViewModelKey
import bitcoin.charts.ui.MarketPriceChartFragment
import bitcoin.charts.ui.MarketPriceChartViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Dagger module for declaring UI layer dependencies for charts module.
 */
@Module
abstract class ChartsUiModule {

    @ContributesAndroidInjector
    abstract fun marketPriceChartFragment(): MarketPriceChartFragment

    @Binds
    @IntoMap
    @ViewModelKey(MarketPriceChartViewModel::class)
    abstract fun marketPriceChartViewModel(viewModel: MarketPriceChartViewModel): ViewModel
}

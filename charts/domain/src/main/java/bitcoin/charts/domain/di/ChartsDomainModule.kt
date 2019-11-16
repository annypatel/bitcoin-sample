package bitcoin.charts.domain.di

import bitcoin.charts.domain.GetMarketPrice
import bitcoin.charts.domain.GetMarketPriceImpl
import dagger.Binds
import dagger.Module

/**
 * Module to inject domain use-cases into application component for charts feature.
 */
@Module
abstract class ChartsDomainModule {

    @Binds
    abstract fun getMarketPrice(getMarketPrice: GetMarketPriceImpl): GetMarketPrice
}

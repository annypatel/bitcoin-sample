package bitcoin.charts.domain.di

import bitcoin.charts.domain.DurationToDate
import bitcoin.charts.domain.DurationToDateImpl
import bitcoin.charts.domain.GetDurationFromBaseline
import bitcoin.charts.domain.GetDurationFromBaselineImpl
import bitcoin.charts.domain.GetMarketPrice
import bitcoin.charts.domain.GetMarketPriceImpl
import bitcoin.charts.domain.ToDisplayablePrice
import bitcoin.charts.domain.ToDisplayablePriceImpl
import dagger.Binds
import dagger.Module

/**
 * Module to inject domain use-cases into application component for charts feature.
 */
@Module
abstract class ChartsDomainModule {

    @Binds
    abstract fun getMarketPrice(getMarketPrice: GetMarketPriceImpl): GetMarketPrice

    @Binds
    abstract fun getDurationFromBaseline(getDurationFromBaseline: GetDurationFromBaselineImpl):
        GetDurationFromBaseline

    @Binds
    abstract fun durationToDate(durationToDate: DurationToDateImpl): DurationToDate

    @Binds
    abstract fun toDisplayablePrice(toDisplayablePrice: ToDisplayablePriceImpl): ToDisplayablePrice
}

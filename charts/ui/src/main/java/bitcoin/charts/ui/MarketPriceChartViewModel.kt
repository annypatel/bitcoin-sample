package bitcoin.charts.ui

import bitcoin.base.domain.RxSchedulers
import bitcoin.base.ui.BaseViewModel
import bitcoin.base.ui.LiveResult
import bitcoin.base.ui.MutableLiveResult
import bitcoin.base.ui.Result
import bitcoin.charts.domain.BaselinedPrices
import bitcoin.charts.domain.DurationToDate
import bitcoin.charts.domain.GetDurationFromBaseline
import bitcoin.charts.domain.GetMarketPrice
import bitcoin.charts.domain.Interval
import bitcoin.charts.domain.Price
import bitcoin.charts.domain.ToDisplayablePrice
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import org.threeten.bp.Instant
import org.threeten.bp.temporal.ChronoUnit
import javax.inject.Inject

/**
 * ViewModel for Bitcoin market price chart.
 */
class MarketPriceChartViewModel @Inject constructor(
    private val getMarketPrice: GetMarketPrice,
    private val durationFromBaseline: GetDurationFromBaseline,
    private val durationToDate: DurationToDate,
    private val toDisplayablePrice: ToDisplayablePrice,
    private val schedulers: RxSchedulers
) : BaseViewModel() {

    private val _prices = MutableLiveResult<BaselinedPrices>()
    val prices: LiveResult<BaselinedPrices> get() = _prices

    fun getMarketPrice(timespan: Interval, rollingAvg: Interval, rollingAvgUnit: ChronoUnit) {

        _prices.value = Result.start()
        getMarketPrice(GetMarketPrice.Args(timespan, rollingAvg))
            .flatMap {
                if (it.isEmpty()) {
                    Single.error(RuntimeException())
                } else {
                    durationFromBaseline(GetDurationFromBaseline.Args(it, rollingAvgUnit))
                }
            }
            .observeOn(schedulers.main)
            .subscribeBy(
                onSuccess = {
                    _prices.value = Result.value(it)
                },
                onError = {
                    _prices.value = Result.error(R.string.error_generic)
                }
            )
            .autoDispose()
    }

    fun convertToDate(duration: Long, durationUnit: ChronoUnit, baseline: Instant): String {
        return durationToDate(DurationToDate.Args(duration, durationUnit, baseline))
    }

    fun displayablePrice(price: Price): String {
        return toDisplayablePrice(price)
    }
}

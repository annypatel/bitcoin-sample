package bitcoin.charts.domain

import bitcoin.base.domain.RxSchedulers
import bitcoin.base.domain.SingleUseCase
import bitcoin.charts.domain.GetDurationFromBaseline.Args
import io.reactivex.Single
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.temporal.ChronoUnit
import javax.inject.Inject

/**
 * Finds the baseline price(oldest) and calculates the time duration from baseline for all prices.
 */
interface GetDurationFromBaseline : SingleUseCase<Args, BaselinedPrices> {

    data class Args(
        val prices: List<Price>,
        val durationUnit: ChronoUnit
    )
}

/**
 * Implementation of [GetDurationFromBaseline], sorts the prices based on their timestamp to find
 * the baseline and calculates the time duration from baseline.
 */
class GetDurationFromBaselineImpl @Inject constructor(
    private val schedulers: RxSchedulers
) : GetDurationFromBaseline {

    override fun invoke(input: Args): Single<BaselinedPrices> {
        return Single.just(input)
            .map { i ->
                val sorted = i.prices.sortedBy { it.timestamp }
                val baseline = sorted[0]

                val baselinedPrices = sorted.map {
                    BaselinedPrice(
                        duration = duration(baseline, it, i.durationUnit),
                        price = it
                    )
                }
                BaselinedPrices(baseline, baselinedPrices)
            }
            .subscribeOn(schedulers.computation)
    }

    private val zoneId = ZoneId.systemDefault()

    private fun duration(
        baseline: Price,
        current: Price,
        durationUnit: ChronoUnit
    ): Long {
        val baselineDate = LocalDateTime.ofInstant(baseline.timestamp, zoneId)
        val currentDate = LocalDateTime.ofInstant(current.timestamp, zoneId)
        return baselineDate.until(currentDate, durationUnit)
    }
}

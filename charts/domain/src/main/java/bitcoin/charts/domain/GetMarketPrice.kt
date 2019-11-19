package bitcoin.charts.domain

import bitcoin.base.domain.SingleUseCase
import bitcoin.charts.domain.GetMarketPrice.Args
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case to get Bitcoin market price.
 */
interface GetMarketPrice : SingleUseCase<Args, List<Price>> {

    data class Args(
        val timespan: Interval,
        val rollingAvg: Interval
    )
}

/**
 * Implementation onf [GetMarketPrice], backed by [BitcoinRepository].
 */
class GetMarketPriceImpl @Inject constructor(
    private val bitcoinRepository: BitcoinRepository
) : GetMarketPrice {

    override fun invoke(input: Args): Single<List<Price>> {
        return bitcoinRepository.getMarketPrice(input.timespan, input.rollingAvg)
    }
}

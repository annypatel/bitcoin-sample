package bitcoin.charts.domain

import bitcoin.base.domain.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case to get Bitcoin market price.
 */
interface GetMarketPrice : SingleUseCase<Unit, List<Price>>

/**
 * Implementation onf [GetMarketPrice], backed by [BitcoinRepository].
 */
class GetMarketPriceImpl @Inject constructor(
    private val bitcoinRepository: BitcoinRepository
) : GetMarketPrice {

    override fun invoke(input: Unit): Single<List<Price>> {
        return bitcoinRepository.getMarketPrice()
    }
}

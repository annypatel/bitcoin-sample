package bitcoin.charts.data

import bitcoin.base.domain.RxSchedulers
import bitcoin.charts.data.network.BitcoinService
import bitcoin.charts.domain.BitcoinRepository
import bitcoin.charts.domain.Price
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of [BitcoinRepository], backed by [BitcoinService], a retrofit service.
 */
class BitcoinRepositoryImpl @Inject constructor(
    private val bitcoinService: BitcoinService,
    private val schedulers: RxSchedulers
) : BitcoinRepository {

    override fun getMarketPrice(): Single<List<Price>> {
        return bitcoinService.getMarketPrice(
            timespan = "1year",
            rollingAverage = "1days",
            format = "json"
        )
            .subscribeOn(schedulers.io)
            .map { response ->
                response.prices.map {
                    Price(
                        timestamp = it.timestamp,
                        price = it.price
                    )
                }
            }
    }
}

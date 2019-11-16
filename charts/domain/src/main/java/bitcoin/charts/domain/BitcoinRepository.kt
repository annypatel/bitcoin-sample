package bitcoin.charts.domain

import io.reactivex.Single

/**
 * Repository to fetch the Bitcoin price.
 */
interface BitcoinRepository {

    fun getMarketPrice(): Single<List<Price>>
}

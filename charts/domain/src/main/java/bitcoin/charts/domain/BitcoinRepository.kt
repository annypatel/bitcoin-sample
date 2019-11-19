package bitcoin.charts.domain

import io.reactivex.Single

/**
 * Repository to fetch the Bitcoin price.
 */
interface BitcoinRepository {

    fun getMarketPrice(timespan: Interval, rollingAvg: Interval): Single<List<Price>>
}

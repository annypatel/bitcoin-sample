package bitcoin.charts.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit service to fetch the Bitcoin price.
 */
interface BitcoinService {

    @GET("market-price")
    fun getMarketPrice(
        @Query("timespan") timespan: String,
        @Query("rollingAverage") rollingAverage: String,
        @Query("format") format: String
    ): Single<MarketPriceResponse>
}

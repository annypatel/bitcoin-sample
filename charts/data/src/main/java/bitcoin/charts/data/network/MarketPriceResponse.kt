package bitcoin.charts.data.network

import com.google.gson.annotations.SerializedName

/**
 * Data class for Bitcoin market price response.
 */
class MarketPriceResponse(
    @SerializedName("values") val prices: List<RawPrice>
)

/**
 * Data class for Bitcoin price with a timestamp.
 */
data class RawPrice(
    @SerializedName("x") val timestamp: Long,
    @SerializedName("y") val price: Double
)

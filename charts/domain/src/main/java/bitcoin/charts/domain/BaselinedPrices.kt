package bitcoin.charts.domain

/**
 * Data class to represent the [prices][BaselinedPrice] with a baseline.
 */
data class BaselinedPrices(
    val baseline: Price,
    val prices: List<BaselinedPrice>
)

/**
 * Data class to represent [Price] with duration from baseline.
 */
data class BaselinedPrice(
    val duration: Long,
    val price: Price
)

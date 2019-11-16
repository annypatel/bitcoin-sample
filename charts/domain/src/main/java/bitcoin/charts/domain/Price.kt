package bitcoin.charts.domain

/**
 * Data class for Bitcoin price with a timestamp.
 */
data class Price(
    val timestamp: Long,
    val price: Double
)

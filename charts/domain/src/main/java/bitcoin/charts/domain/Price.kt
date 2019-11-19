package bitcoin.charts.domain

import org.threeten.bp.Instant

/**
 * Data class for Bitcoin price with a timestamp.
 */
data class Price(
    val timestamp: Instant,
    val price: Float
)

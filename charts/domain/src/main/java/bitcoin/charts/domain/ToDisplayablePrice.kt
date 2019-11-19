package bitcoin.charts.domain

import bitcoin.base.domain.UseCase
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

/**
 * Converts [Price] into a human readable string.
 */
interface ToDisplayablePrice : UseCase<Price, String>

/**
 * Implementation of [ToDisplayablePrice], which returns a price in '`yyyy/MM/dd hh:mm\n$ %.0f`'
 * format.
 */
class ToDisplayablePriceImpl @Inject constructor() : ToDisplayablePrice {

    private val displayFormat = "%s\n$ %.0f"
    private val dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")
        .withZone(ZoneId.systemDefault())

    override fun invoke(input: Price): String {
        val date = dateFormat.format(input.timestamp)
        return displayFormat.format(date, input.price)
    }
}

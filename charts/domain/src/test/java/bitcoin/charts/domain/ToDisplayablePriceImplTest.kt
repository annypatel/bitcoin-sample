package bitcoin.charts.domain

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.threeten.bp.Instant.ofEpochSecond
import java.util.TimeZone

class ToDisplayablePriceImplTest {

    @Test
    fun shouldConvertPriceIntoDisplayableString() {
        // GIVEN
        TimeZone.setDefault(TimeZone.getTimeZone("UTC")) // fixed timezone
        val price = Price(
            timestamp = ofEpochSecond(1574153680), // 19 Nov 2019 08:54:40
            price = 123.45f
        )
        val toDisplayablePrice = ToDisplayablePriceImpl()

        // WHEN
        val result = toDisplayablePrice(price)

        // THEN
        assertThat(result, equalTo("2019/11/19 08:54\n$ 123"))
    }
}

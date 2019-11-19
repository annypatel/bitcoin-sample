package bitcoin.charts.domain

import bitcoin.charts.domain.Interval.Companion.days
import bitcoin.charts.domain.Interval.Companion.years
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import org.threeten.bp.Instant.ofEpochSecond

class GetMarketPriceImplTest {

    private val mockedBitcoinRepository = mock<BitcoinRepository>()
    private val getMarketPrice = GetMarketPriceImpl(mockedBitcoinRepository)

    @Test
    fun shouldEmitValue_whenGetMarketPriceCallSuccessful() {
        // GIVEN
        val prices = listOf(Price(ofEpochSecond(123), 1.23f))
        givenSuccessfulGetMarketPriceCall(prices)
        val args = GetMarketPrice.Args(years(1), days(1))

        // WHEN
        val observer = getMarketPrice(args).test()

        // THEN
        observer.assertValue(prices)
            .assertNoErrors()
    }

    @Test
    fun shouldPropagateError_whenGetMarketPriceCallUnsuccessful() {
        // GIVEN
        val error = Throwable()
        givenUnsuccessfulGetMarketPriceCall(error)
        val args = GetMarketPrice.Args(years(1), days(1))

        // WHEN
        val observer = getMarketPrice(args).test()

        // THEN
        observer.assertError(error)
            .assertNoValues()
    }

    private fun givenSuccessfulGetMarketPriceCall(prices: List<Price>) {
        whenever(mockedBitcoinRepository.getMarketPrice(any(), any()))
            .thenReturn(Single.just(prices))
    }

    private fun givenUnsuccessfulGetMarketPriceCall(error: Throwable) {
        whenever(mockedBitcoinRepository.getMarketPrice(any(), any()))
            .thenReturn(Single.error(error))
    }
}

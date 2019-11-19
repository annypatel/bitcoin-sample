package bitcoin.charts.data

import bitcoin.charts.data.network.BitcoinService
import bitcoin.charts.data.network.MarketPriceResponse
import bitcoin.charts.data.network.RawPrice
import bitcoin.charts.domain.Interval.Companion.days
import bitcoin.charts.domain.Interval.Companion.years
import bitcoin.charts.domain.Price
import bitcoin.test.domain.testRxSchedulers
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import org.threeten.bp.Instant.ofEpochSecond

class BitcoinRepositoryImplTest {

    private val mockedBitcoinService = mock<BitcoinService>()
    private val bitcoinRepository = BitcoinRepositoryImpl(mockedBitcoinService, testRxSchedulers())

    @Test
    fun shouldEmitValue_whenGetMarketPriceCallSuccessful() {
        // GIVEN
        givenSuccessfulGetMarketPriceCall(marketPriceResponse())

        // WHEN
        val observer = bitcoinRepository.getMarketPrice(years(1), days(1))
            .test()

        // THEN
        observer.assertValue(prices())
            .assertNoErrors()
    }

    @Test
    fun shouldPropagateError_whenGetMarketPriceCallUnsuccessful() {
        // GIVEN
        val error = Throwable()
        givenUnsuccessfulGetMarketPriceCall(error)

        // WHEN
        val observer = bitcoinRepository.getMarketPrice(years(1), days(1))
            .test()

        // THEN
        observer.assertError(error)
            .assertNoValues()
    }

    private fun givenSuccessfulGetMarketPriceCall(response: MarketPriceResponse) {
        whenever(mockedBitcoinService.getMarketPrice(any(), any(), any()))
            .thenReturn(Single.just(response))
    }

    private fun givenUnsuccessfulGetMarketPriceCall(error: Throwable) {
        whenever(mockedBitcoinService.getMarketPrice(any(), any(), any()))
            .thenReturn(Single.error(error))
    }

    private fun marketPriceResponse() = MarketPriceResponse(
        listOf(
            RawPrice(
                timestamp = 123,
                price = 1.23f
            ),
            RawPrice(
                timestamp = 456,
                price = 4.56f
            )
        )
    )

    private fun prices() = listOf(
        Price(
            timestamp = ofEpochSecond(123),
            price = 1.23f
        ),
        Price(
            timestamp = ofEpochSecond(456),
            price = 4.56f
        )
    )
}

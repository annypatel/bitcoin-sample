package bitcoin.charts.domain

import bitcoin.base.domain.invoke
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test

class GetMarketPriceImplTest {

    private val mockedBitcoinRepository = mock<BitcoinRepository>()
    private val getMarketPrice = GetMarketPriceImpl(mockedBitcoinRepository)

    @Test
    fun shouldEmitValue_whenGetMarketPriceCallSuccessful() {
        // GIVEN
        val prices = listOf(Price(123, 1.23))
        givenSuccessfulGetMarketPriceCall(prices)

        // WHEN
        val observer = getMarketPrice().test()

        // THEN
        observer.assertValue(prices)
            .assertNoErrors()
    }

    @Test
    fun shouldPropagateError_whenGetMarketPriceCallUnsuccessful() {
        // GIVEN
        val error = Throwable()
        givenUnsuccessfulGetMarketPriceCall(error)

        // WHEN
        val observer = getMarketPrice().test()

        // THEN
        observer.assertError(error)
            .assertNoValues()
    }

    private fun givenSuccessfulGetMarketPriceCall(prices: List<Price>) {
        whenever(mockedBitcoinRepository.getMarketPrice())
            .thenReturn(Single.just(prices))
    }

    private fun givenUnsuccessfulGetMarketPriceCall(error: Throwable) {
        whenever(mockedBitcoinRepository.getMarketPrice())
            .thenReturn(Single.error(error))
    }
}

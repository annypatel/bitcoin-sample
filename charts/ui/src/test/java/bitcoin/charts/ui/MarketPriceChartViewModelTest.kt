package bitcoin.charts.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import bitcoin.charts.domain.BaselinedPrice
import bitcoin.charts.domain.BaselinedPrices
import bitcoin.charts.domain.DurationToDate
import bitcoin.charts.domain.GetDurationFromBaseline
import bitcoin.charts.domain.GetMarketPrice
import bitcoin.charts.domain.Interval.Companion.days
import bitcoin.charts.domain.Interval.Companion.years
import bitcoin.charts.domain.Price
import bitcoin.charts.domain.ToDisplayablePrice
import bitcoin.test.domain.testRxSchedulers
import bitcoin.test.ui.isError
import bitcoin.test.ui.isStart
import bitcoin.test.ui.isValue
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.Instant.EPOCH
import org.threeten.bp.Instant.ofEpochSecond
import org.threeten.bp.temporal.ChronoUnit

class MarketPriceChartViewModelTest {

    @get:Rule val instantExecutor = InstantTaskExecutorRule()

    private val mockedGetMarketPrice = mock<GetMarketPrice>()
    private val mockedDurationFromBaseline = mock<GetDurationFromBaseline>()
    private val mockedDurationToDate = mock<DurationToDate>()
    private val mockedToDisplayablePrice = mock<ToDisplayablePrice>()

    private val testScheduler = TestScheduler()
    private val viewModel = MarketPriceChartViewModel(
        mockedGetMarketPrice,
        mockedDurationFromBaseline,
        mockedDurationToDate,
        mockedToDisplayablePrice,
        testRxSchedulers(main = testScheduler)
    )

    @Test
    fun shouldSetPrices_whenGetMarketPriceSuccessful() {
        // GIVEN
        givenSuccessfulGetMarketPriceCall(prices())
        val baselinedPrices = baselinedPrices()
        givenSuccessfulDurationFromBaselineCall(baselinedPrices)

        // WHEN
        viewModel.getMarketPrice(years(1), days(1), ChronoUnit.DAYS)

        // THEN
        assertThat(viewModel.prices.value, isStart())
        testScheduler.triggerActions()
        assertThat(viewModel.prices.value, isValue(baselinedPrices))
    }

    @Test
    fun shouldSetError_whenGetMarketPriceReturnsEmpty() {
        // GIVEN
        givenSuccessfulGetMarketPriceCall(emptyList())

        // WHEN
        viewModel.getMarketPrice(years(1), days(1), ChronoUnit.DAYS)

        // THEN
        assertThat(viewModel.prices.value, isStart())
        testScheduler.triggerActions()
        assertThat(viewModel.prices.value, isError(R.string.error_generic))
    }

    @Test
    fun shouldSetError_whenGetMarketPriceUnsuccessful() {
        // GIVEN
        givenUnsuccessfulGetMarketPriceCall(RuntimeException())

        // WHEN
        viewModel.getMarketPrice(years(1), days(1), ChronoUnit.DAYS)

        // THEN
        assertThat(viewModel.prices.value, isStart())
        testScheduler.triggerActions()
        assertThat(viewModel.prices.value, isError(R.string.error_generic))
    }

    @Test
    fun shouldConvertDurationToDate() {
        // GIVEN
        val expectedDate = "19 Nov"
        givenSuccessfulDurationToDateCall(expectedDate)

        // WHEN
        val result = viewModel.convertToDate(1, ChronoUnit.DAYS, EPOCH)

        // THEN
        assertThat(result, equalTo(expectedDate))
    }

    @Test
    fun shouldReturnPriceInDisplayableFormat() {
        // GIVEN
        val formattedPrice = "19 Nov\n$ 123"
        givenSuccessfulToDisplayablePrice(formattedPrice)

        // WHEN
        val result = viewModel.displayablePrice(price1())

        // THEN
        assertThat(result, equalTo(formattedPrice))
    }

    private fun givenSuccessfulGetMarketPriceCall(prices: List<Price>) {
        whenever(mockedGetMarketPrice(any()))
            .thenReturn(Single.just(prices))
    }

    private fun givenUnsuccessfulGetMarketPriceCall(error: Throwable) {
        whenever(mockedGetMarketPrice(any()))
            .thenReturn(Single.error(error))
    }

    private fun givenSuccessfulDurationFromBaselineCall(prices: BaselinedPrices) {
        whenever(mockedDurationFromBaseline(any()))
            .thenReturn(Single.just(prices))
    }

    @Suppress("SameParameterValue")
    private fun givenSuccessfulDurationToDateCall(date: String) {
        whenever(mockedDurationToDate(any()))
            .thenReturn(date)
    }

    @Suppress("SameParameterValue")
    private fun givenSuccessfulToDisplayablePrice(formattedPrice: String) {
        whenever(mockedToDisplayablePrice(any()))
            .thenReturn(formattedPrice)
    }

    private fun prices() = listOf(
        price2(),
        price1()
    )

    private fun baselinedPrices() = BaselinedPrices(
        baseline = price1(),
        prices = listOf(
            BaselinedPrice(
                duration = 0,
                price = price1()
            ),
            BaselinedPrice(
                duration = 1,
                price = price2()
            )
        )
    )

    private fun price1() = Price(
        timestamp = ofEpochSecond(1573776000), // 2019 Nov 15
        price = 1.23f
    )

    private fun price2() = Price(
        timestamp = ofEpochSecond(1573862400), // 2019 Nov 16
        price = 4.56f
    )
}

package bitcoin.charts.domain

import bitcoin.test.domain.testRxSchedulers
import org.junit.Test
import org.threeten.bp.Instant.ofEpochSecond
import org.threeten.bp.temporal.ChronoUnit

class GetDurationFromBaselineImplTest {

    @Test
    fun shouldFindBaselineAndCalculateDurationFromIt() {
        // GIVEN
        val prices = prices()
        val getDurationFromBaseline = GetDurationFromBaselineImpl(testRxSchedulers())
        val args = GetDurationFromBaseline.Args(prices, ChronoUnit.DAYS)

        // WHEN
        val observer = getDurationFromBaseline(args)
            .test()

        // THEN
        observer.assertValue(baselinedPrices())
            .assertNoErrors()
    }

    private fun prices() = listOf(
        price3(),
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
            ),
            BaselinedPrice(
                duration = 2,
                price = price3()
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

    private fun price3() = Price(
        timestamp = ofEpochSecond(1573948800), // 2019 Nov 17
        price = 7.89f
    )
}

package bitcoin.charts.ui

import bitcoin.test.ui.okReplayRule
import okreplay.OkReplay
import org.junit.Rule
import org.junit.Test

class MarketPriceChartFragmentTest {

    @get:Rule val okReplay = okReplayRule()

    @Test
    @OkReplay
    fun shouldShowMarketPriceChart_whenApiCallSuccessful() = marketPriceChart {
        // WHEN
        launch()

        // THEN
        chartShouldBeVisible()
        progressBarShouldNotBeVisible()
        errorViewShouldNotBeVisible()
    }

    @Test
    @OkReplay
    fun shouldShowErrorView_whenNoDataFound() = marketPriceChart {
        // WHEN
        launch()

        // THEN
        chartShouldNotBeVisible()
        progressBarShouldNotBeVisible()
        errorViewShouldBeVisible()
    }

    @Test
    @OkReplay
    fun shouldShowErrorView_whenApiCallUnsuccessful() = marketPriceChart {
        // WHEN
        launch()

        // THEN
        chartShouldNotBeVisible()
        progressBarShouldNotBeVisible()
        errorViewShouldBeVisible()
    }

    @Test
    @OkReplay
    fun shouldRetryAgain_whenApiCallUnsuccessful() = marketPriceChart {
        // GIVEN
        launch()

        // WHEN
        clickRetry()

        // THEN
        chartShouldNotBeVisible()
        progressBarShouldNotBeVisible()
        errorViewShouldBeVisible()
    }
}

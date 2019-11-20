package bitcoin.charts.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.CoreMatchers.not

inline fun marketPriceChart(init: MarketPriceChartRobot.() -> Unit): Unit =
    MarketPriceChartRobot().init()

class MarketPriceChartRobot {

    fun launch() {
        launchFragmentInContainer<MarketPriceChartFragment>(themeResId = R.style.Theme_App)
    }

    fun clickRetry() {
        onView(withId(R.id.btnRetry))
            .perform(click())
    }

    fun chartShouldBeVisible() {
        onView(withId(R.id.marketPriceChart))
            .check(matches(isDisplayed()))
    }

    fun chartShouldNotBeVisible() {
        onView(withId(R.id.marketPriceChart))
            .check(matches(not(isDisplayed())))
    }

    fun progressBarShouldNotBeVisible() {
        onView(withId(R.id.progressBar))
            .check(matches(not(isDisplayed())))
    }

    fun errorViewShouldNotBeVisible() {
        onView(withId(R.id.viewError))
            .check(matches(not(isDisplayed())))
    }

    fun errorViewShouldBeVisible() {
        onView(withId(R.id.viewError))
            .check(matches(isDisplayed()))
    }
}

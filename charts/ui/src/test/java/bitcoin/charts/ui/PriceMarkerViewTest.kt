package bitcoin.charts.ui

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import bitcoin.charts.domain.Price
import bitcoin.test.ui.themedContext
import com.github.mikephil.charting.data.Entry
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.Instant.ofEpochSecond

@RunWith(AndroidJUnit4::class)
class PriceMarkerViewTest {

    private val mockedViewModel = mock<MarketPriceChartViewModel>()
    private val markerView = PriceMarkerView(
        themedContext(R.style.Theme_AppCompat),
        mockedViewModel
    )

    @Test
    fun shouldDisplayPriceWithDate_whenEntryHasPrice() {
        // GIVEN
        val expectedValue = "2019/11/19 08:54\n$ 123"
        givenSuccessfulViewModelCall(expectedValue)
        val entry = Entry(
            1f,
            1f,
            Price(
                timestamp = ofEpochSecond(1574153680), // 19 Nov 2019 08:54:40
                price = 123.45f
            )
        )

        // WHEN
        markerView.refreshContent(entry, mock())

        // THEN
        assertThat(markerView.tvPrice, withText(expectedValue))
        assertThat(markerView.tvPrice, isDisplayed())
    }

    @Test
    fun shouldHidePrice_whenEntryIsEmpty() {
        // GIVEN
        val entry = Entry(1f, 1f)

        // WHEN
        markerView.refreshContent(entry, mock())

        // THEN
        assertThat(markerView.tvPrice, withText(""))
        assertThat(markerView.tvPrice, not(isDisplayed()))
    }

    @Test
    fun shouldReturnOffset() {
        // WHEN
        val offset = markerView.offset

        // THEN
        assertThat(offset.x, equalTo(-markerView.width.toFloat() / 2))
        assertThat(offset.y, equalTo(-markerView.height.toFloat()))
    }

    @Suppress("SameParameterValue")
    private fun givenSuccessfulViewModelCall(expectedPrice: String) {
        whenever(mockedViewModel.displayablePrice(any()))
            .thenReturn(expectedPrice)
    }
}

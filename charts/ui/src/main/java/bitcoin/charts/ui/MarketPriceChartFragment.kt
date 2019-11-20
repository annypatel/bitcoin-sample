package bitcoin.charts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import bitcoin.base.ui.observe
import bitcoin.charts.domain.BaselinedPrices
import bitcoin.charts.domain.Interval.Companion.days
import bitcoin.charts.domain.Interval.Companion.years
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import dagger.android.support.DaggerFragment
import org.threeten.bp.temporal.ChronoUnit
import javax.inject.Inject

/**
 * Fragment to display Bitcoin market price chart.
 */
class MarketPriceChartFragment : DaggerFragment() {

    companion object {
        private val TIMESPAN = years(1)
        private val ROLLING_AVG = days(1)
        private val ROLLING_AVG_UNIT = ChronoUnit.DAYS
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: MarketPriceChartViewModel by viewModels { factory }

    private lateinit var progressBar: ProgressBar
    private lateinit var viewError: View
    private lateinit var tvError: TextView
    private lateinit var chart: LineChart

    private val chartLineWidth by lazy {
        resources.getDimensionPixelSize(R.dimen.chart_line_width).toFloat()
    }
    private val chartLineColor by lazy {
        getColor(requireContext(), R.color.chart_line_color)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_market_price_chart, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressBar = view.findViewById(R.id.progressBar)
        viewError = view.findViewById(R.id.viewError)
        tvError = view.findViewById(R.id.tvError)

        // setup retry
        view.findViewById<View>(R.id.btnRetry)
            .setOnClickListener { getMarketPrice() }

        // configure line chart
        chart = view.findViewById<LineChart>(R.id.marketPriceChart)
            .apply {
                axisRight.isEnabled = false
                xAxis.position = BOTTOM
                xAxis.granularity = 1f
                xAxis.isGranularityEnabled = true
                xAxis.setDrawGridLines(false)
                legend.isEnabled = false
                description.isEnabled = false
                marker = PriceMarkerView(requireContext(), viewModel)
            }

        // observe and get the bitcoin market price
        viewModel.prices.observe(this, ::showLoading, ::showPriceOnChart, ::showError)
        savedInstanceState ?: getMarketPrice()
    }

    private fun getMarketPrice() = viewModel.getMarketPrice(TIMESPAN, ROLLING_AVG, ROLLING_AVG_UNIT)

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        viewError.visibility = View.GONE
        chart.visibility = View.GONE
    }

    private fun showPriceOnChart(prices: BaselinedPrices) {
        // convert values on x-axis to date
        chart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String =
                viewModel.convertToDate(
                    value.toLong(),
                    ROLLING_AVG_UNIT,
                    prices.baseline.timestamp
                )
        }

        // create data-set
        val entries = prices.prices.map {
            Entry(
                it.duration.toFloat(),
                it.price.price,
                it.price
            )
        }
        val dataSet = LineDataSet(entries, "")
            .apply {
                lineWidth = chartLineWidth
                color = chartLineColor
                setDrawCircles(false)
                setDrawValues(false)
            }

        // show price in chart
        progressBar.visibility = View.GONE
        chart.visibility = View.VISIBLE
        chart.data = LineData(dataSet)
        chart.invalidate()
    }

    private fun showError(@StringRes resId: Int) {
        progressBar.visibility = View.GONE
        viewError.visibility = View.VISIBLE
        tvError.setText(resId)
    }
}

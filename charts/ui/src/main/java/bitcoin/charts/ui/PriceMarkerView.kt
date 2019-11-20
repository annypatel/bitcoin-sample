package bitcoin.charts.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.TextView
import bitcoin.charts.domain.Price
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

/**
 * Marker view for the price.
 */
@SuppressLint("ViewConstructor")
class PriceMarkerView(
    context: Context,
    private val viewModel: MarketPriceChartViewModel
) : MarkerView(context, R.layout.price_marker_view) {

    val tvPrice: TextView = findViewById(R.id.tvPrice)

    override fun refreshContent(entry: Entry, highlight: Highlight) {
        when (val data = entry.data) {
            is Price -> {
                tvPrice.text = viewModel.displayablePrice(data)
                tvPrice.visibility = View.VISIBLE
            }
            else -> {
                tvPrice.text = ""
                tvPrice.visibility = View.GONE
            }
        }

        super.refreshContent(entry, highlight)
    }

    override fun getOffset(): MPPointF {
        // make marker view to appear above the selected entry in graph
        return MPPointF(
            -(width / 2).toFloat(),
            -(height).toFloat()
        )
    }
}

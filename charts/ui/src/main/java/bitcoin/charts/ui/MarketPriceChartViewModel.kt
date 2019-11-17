package bitcoin.charts.ui

import bitcoin.base.domain.RxSchedulers
import bitcoin.base.ui.BaseViewModel
import bitcoin.charts.domain.GetMarketPrice
import javax.inject.Inject

/**
 * ViewModel for Bitcoin market price chart.
 */
class MarketPriceChartViewModel @Inject constructor(
    getMarketPrice: GetMarketPrice,
    schedulers: RxSchedulers
) : BaseViewModel()

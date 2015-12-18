package com.benny.app.sample.viewmodel

import com.benny.app.sample.model.Stock
import com.benny.library.neobinding.bind.ViewModel
import com.benny.library.neobinding.bind.Command
import com.benny.library.neobinding.bind.ItemViewModel
import kotlin.properties.Delegates

/**
 * Created by benny on 11/19/15.
 */
class StockViewModel() : ItemViewModel<Stock>() {
    public var stock: Stock? by Delegates.bindProperty<Stock>("stock")

    val name: String? by Delegates.bindProperty("name", "stock", { stock?.cnName ?: "" })
    val symbol: String? by Delegates.bindProperty("symbol", "stock", { stock?.symbol ?: "" })
    val price: Float? by Delegates.bindProperty("price", "stock", { stock?.realtimePrice ?: 0f })
    val changePercent: Float? by Delegates.bindProperty("changePercent", "stock", { stock?.changePercent ?: 0f })
    val listedState: Int? by Delegates.bindProperty("listedState", "stock", { stock?.listedState ?: Stock.LISTED_STATE_ABNORMAL })

    override fun updateData(t: Stock?) {
        stock = t
    }
}
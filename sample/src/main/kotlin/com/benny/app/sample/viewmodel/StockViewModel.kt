package com.benny.app.sample.viewmodel

import com.benny.app.sample.model.Stock
import com.benny.library.kbinding.bind.ViewModel
import com.benny.library.kbinding.bind.Command
import com.benny.library.kbinding.bind.ItemViewModel
import kotlin.properties.Delegates

/**
 * Created by benny on 11/19/15.
 */
class StockViewModel() : ItemViewModel<Stock>() {
    public var stock: Stock? by bindProperty<Stock>("stock")

    val name: String? by bindProperty("name", "stock", { stock?.cnName ?: "" })
    val symbol: String? by bindProperty("symbol", "stock", { stock?.symbol ?: "" })
    val price: Float? by bindProperty("price", "stock", { stock?.realtimePrice ?: 0f })
    val changePercent: Float? by bindProperty("changePercent", "stock", { stock?.changePercent ?: 0f })
    val listedState: Int? by bindProperty("listedState", "stock", { stock?.listedState ?: Stock.LISTED_STATE_ABNORMAL })

    override fun updateData(t: Stock?) {
        stock = t
    }
}
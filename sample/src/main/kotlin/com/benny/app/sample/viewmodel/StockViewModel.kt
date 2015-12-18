package com.benny.app.sample.viewmodel

import com.benny.app.sample.model.Stock
import com.benny.library.neobinding.bind.ViewModel
import com.benny.library.neobinding.bind.Command
import kotlin.properties.Delegates

/**
 * Created by benny on 11/19/15.
 */
class StockViewModel() : ViewModel() {

    public var stock: Stock? by Delegates.bindProperty<Stock>("stock")

    val name: String? by Delegates.bindProperty("name", listOf("stock"), { stock?.cnName ?: "" })
    val symbol: String? by Delegates.bindProperty("symbol", listOf("stock"), { stock?.symbol ?: "" })
    val price: Float? by Delegates.bindProperty("price", listOf("stock"), { stock?.realtimePrice ?: 0f })
    val changePercent: Float? by Delegates.bindProperty("changePercent", listOf("stock"), { stock?.changePercent ?: 0f })
    val listedState: Int? by Delegates.bindProperty("listedState", listOf("stock"), { stock?.listedState ?: Stock.LISTED_STATE_ABNORMAL })

}
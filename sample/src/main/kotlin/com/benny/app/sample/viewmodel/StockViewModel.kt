package com.benny.app.sample.viewmodel

import android.util.Log
import com.benny.app.sample.network.service.caishuo.model.MarketType
import com.benny.app.sample.network.service.caishuo.model.Stock
import com.benny.library.kbinding.bind.ItemViewModel

/**
 * Created by benny on 11/19/15.
 */
class StockViewModel() : ItemViewModel<Stock>() {
    var stock: Stock? by bindProperty("stock")

    val name: String? by bindProperty("name", "stock") { stock!!.cnName }
    val symbol: String? by bindProperty("symbol", "stock") { stock!!.symbol }
    val price: Float? by bindProperty("price", "stock") { stock!!.realtimePrice }
    val changePrice: Float? by bindProperty("changePrice", "stock") { stock!!.changePrice }
    val changePercent: Float? by bindProperty("changePercent", "stock") { stock!!.changePercent }
    val listedState: Int? by bindProperty("listedState", "stock") { stock!!.listedState}
    val market: MarketType? by bindProperty("market", "stock") { stock!!.market }

    override fun updateData(t: Stock?) {
        stock = t
        Log.d("BaseListAdapter", "notifyPropertyChange: data is $t")
    }
}
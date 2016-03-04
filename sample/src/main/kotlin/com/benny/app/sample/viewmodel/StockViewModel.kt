package com.benny.app.sample.viewmodel

import com.benny.app.sample.network.service.caishuo.model.Stock
import com.benny.library.kbinding.annotation.ExtractProperty
import com.benny.library.kbinding.bind.ItemViewModel
import com.benny.library.kbinding.internal.BindingInitializer
import kotlin.properties.Delegates

/**
 * Created by benny on 11/19/15.
 */
class StockViewModel() : ItemViewModel<Stock>() {

    @delegate:ExtractProperty("name", "symbol", "realtimePrice", "changePercent", "changePrice", "listedState", "market")
    var stock: Stock? by Delegates.property()

    override fun updateData(t: Stock?) {
        stock = t
    }
}
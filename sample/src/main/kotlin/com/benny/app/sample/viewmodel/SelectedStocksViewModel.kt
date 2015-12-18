package com.benny.app.sample.viewmodel

import com.benny.app.sample.model.Stock
import com.benny.app.sample.network.service.caishuo.CaishuoService
import com.benny.library.neobinding.bind.ViewModel
import kotlin.properties.Delegates

/**
 * Created by benny on 12/18/15.
 */

class SelectedStocksViewModel : ViewModel() {
    public var stocks: List<Stock>? by Delegates.bindProperty<List<Stock>>("stocks")

    fun fetchStocks() {
        CaishuoService.getInstance().followedStocks("1301").subscribe { stocks = it }
    }
}
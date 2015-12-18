package com.benny.app.sample

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import com.benny.app.sample.extension.bindingContext
import com.benny.app.sample.model.Stock
import com.benny.app.sample.network.service.caishuo.CaishuoService
import com.benny.app.sample.viewcomponent.StockItemView
import com.trello.rxlifecycle.components.support.RxFragmentActivity
import org.jetbrains.anko.*

import com.benny.app.sample.viewmodel.LoginViewModel
import com.benny.app.sample.viewmodel.StockViewModel
import com.benny.library.neobinding.bind.*
import com.benny.library.neobinding.drawable.*
import com.benny.library.neobinding.view.*
import com.benny.library.neobinding.converter.*

import com.benny.library.neobinding.extension.*

class StockActivity : RxFragmentActivity() {
    val stockViewModel = StockViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApplicationContext.init(this)

        StockItemView().setContentView(this).bindTo(bindingContext(this), stockViewModel)
        CaishuoService.getInstance().followedStocks("1301").subscribe { updateStock(it) }
    }

    fun updateStock(stocks: List<Stock>) {
        stockViewModel.stock = stocks.first()
    }
}

package com.benny.app.sample

import android.graphics.Color
import android.os.Bundle
import com.benny.app.sample.extension.bindingContext
import com.benny.app.sample.model.Stock
import com.benny.app.sample.viewcomponent.StockItemView
import com.benny.app.sample.viewmodel.SelectedStocksViewModel
import com.trello.rxlifecycle.components.support.RxFragmentActivity
import com.benny.app.sample.viewmodel.StockViewModel
import com.benny.library.neobinding.bind.BindingContext
import com.benny.library.neobinding.converter.ListToAdapterConverter
import com.benny.library.neobinding.drawable.color
import com.benny.library.neobinding.extension.*
import com.benny.library.neobinding.view.ViewBinderComponent
import com.benny.library.neobinding.view.ViewCreator
import org.jetbrains.anko.*

class StockActivity : RxFragmentActivity() {
    val selectedStocksViewModel = SelectedStocksViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApplicationContext.init(this)

        StockActivityUI(bindingContext(this)).setContentView(this).bindTo(bindingContext(this), selectedStocksViewModel)
        selectedStocksViewModel.fetchStocks()
    }

    class StockActivityUI(val bindingContext: BindingContext) : ViewBinderComponent<StockActivityUI> {
        override fun builder(): AnkoContext<*>.() -> Unit = {
            val viewCreator = ViewCreator<Stock>(bindingContext, StockItemView(), {StockViewModel()})
            relativeLayout() {
                listView {
                    dividerHeight = dip(-1)
                    selector = color { color = Color.TRANSPARENT }
                    bind { adapter(path = "stocks", converter = ListToAdapterConverter(viewCreator)) }
                }.lparams(matchParent, matchParent)
            }
        }
    }
}

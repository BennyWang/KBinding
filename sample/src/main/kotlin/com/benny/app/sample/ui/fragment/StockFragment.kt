package com.benny.app.sample.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benny.app.sample.ApplicationContext
import com.benny.app.sample.extension.bindingContext
import com.benny.app.sample.model.Stock
import com.benny.app.sample.viewcomponent.StockItemView
import com.benny.app.sample.viewmodel.SelectedStocksViewModel
import com.trello.rxlifecycle.components.support.RxFragmentActivity
import com.benny.app.sample.viewmodel.StockViewModel
import com.benny.library.neobinding.bind.BindingContext
import com.benny.library.neobinding.converter.ListToRecyclerAdapterConverter
import com.benny.library.neobinding.extension.*
import com.benny.library.neobinding.view.ViewBinderComponent
import com.benny.library.neobinding.view.ViewCreator
import com.trello.rxlifecycle.components.support.RxFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.act

class StockFragment : RxFragment() {
    val selectedStocksViewModel = SelectedStocksViewModel()
    var contentView: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(contentView == null) {
            contentView = StockFragmentUI().createViewBinder(act).bindTo(bindingContext(act), selectedStocksViewModel)
            selectedStocksViewModel.fetchStocks()
        }
        return contentView
    }

    inner class StockFragmentUI() : ViewBinderComponent<StockFragment> {
        override fun builder(): AnkoContext<*>.() -> Unit = {
            val viewCreator = ViewCreator<Stock>(bindingContext(act), StockItemView(), { StockViewModel() })
            relativeLayout() {
                recyclerView {
                    bind { adapter(path = "stocks", converter = ListToRecyclerAdapterConverter(viewCreator)) }
                }.lparams(matchParent, matchParent)
            }
        }
    }
}

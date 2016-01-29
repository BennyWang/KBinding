package com.benny.app.sample.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benny.app.sample.SampleApplication
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.act
import com.benny.library.kbinding.view.ViewBinderComponent
import com.benny.app.sample.network.service.caishuo.model.Stock
import com.benny.app.sample.network.service.caishuo.CaishuoService
import com.benny.app.sample.ui.activity.StockDetailsActivity
import com.benny.app.sample.viewcomponent.StockItemView
import com.benny.app.sample.viewmodel.StockViewModel
import com.benny.library.kbinding.bind.BindingDelegate
import com.benny.library.kbinding.bind.Command
import com.benny.library.kbinding.converter.ListToAdapterConverter
import com.benny.library.kbinding.dsl.adapter
import com.benny.library.kbinding.dsl.bind
import com.benny.library.kbinding.dsl.itemClick
import org.jetbrains.anko.support.v4.startActivity

class StockFragment : BaseFragment() {
    val bindingDelegate = BindingDelegate()
    var contentView: View? = null

    public var stocks: List<Stock>? by bindingDelegate.bindProperty<List<Stock>>("stocks")

    val stockDetail: Command<Int> by bindingDelegate.bindCommand("stockDetail") { params, canExecute ->
        startActivity<StockDetailsActivity>("id" to stocks!![params].id)
    }

    fun fetchStocks() {
        CaishuoService.getInstance().followedStocks("1301").onErrorReturn { listOf<Stock>() }.subscribe { stocks = it }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("StockFragment", "onCreateView")

        if(contentView == null) {
            contentView = StockFragmentUI().createViewBinder(act, this).bindTo(bindingDelegate)
            fetchStocks()
        }
        return contentView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("StockFragment", "onCreate")
    }

    inner class StockFragmentUI() : ViewBinderComponent<StockFragment> {
        override fun builder(): AnkoContext<StockFragment>.() -> Unit = {
            relativeLayout() {
                backgroundColor = Color.WHITE
                listView {
                    //layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    bind { adapter("stocks", converter = ListToAdapterConverter(owner.viewCreator(StockItemView(), ::StockViewModel))) }
                    dividerHeight = 0
                    bind { itemClick("stockDetail") }
                }.lparams(matchParent, matchParent)
            }
        }
    }
}

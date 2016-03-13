package com.benny.app.sample.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity
import com.benny.library.kbinding.annotation.Property
import com.benny.library.kbinding.annotation.Command
import com.benny.library.kbinding.dsl.bind
import com.benny.library.kbinding.adapterview.bindings.adapter
import com.benny.library.kbinding.adapterview.bindings.itemClick
import com.benny.library.kbinding.adapterview.converter.ListToRecyclerAdapterConverter
import com.benny.library.kbinding.view.ViewBinderComponent

import com.benny.app.sample.network.service.caishuo.CaishuoService
import com.benny.app.sample.network.service.caishuo.model.Stock
import com.benny.app.sample.ui.activity.StockDetailsActivity
import com.benny.app.sample.ui.layout.item.StockItemView
import com.benny.app.sample.utils.generateViewId
import com.benny.app.sample.viewmodel.StockViewModel
import com.benny.library.kbinding.adapterview.viewcreator.viewCreator
import kotlin.properties.Delegates

class StockFragment : BaseFragment() {
    var contentView: View? = null

    @delegate:Property
    var stocks: List<Stock>? by Delegates.property()

    @Command
    fun stockDetail(params: Int) {
        startActivity<StockDetailsActivity>("id" to stocks!![params].id)
    }

    fun fetchStocks() {
        CaishuoService.instance.followedStocks("1301").onErrorReturn { listOf<Stock>() }.subscribe { stocks = it }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("StockFragment", "onCreateView")

        if(contentView == null) {
            contentView = StockFragmentUI().createViewBinder(act, this).bindTo(this)
            fetchStocks()
        }
        return contentView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("StockFragment", "onCreate")
    }

    inner class StockFragmentUI() : ViewBinderComponent<StockFragment> {
        override fun builder(): AnkoContext<*>.() -> Unit = {
            relativeLayout() {
                backgroundColor = Color.WHITE
                recyclerView {
                    id = generateViewId()
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    bind { adapter("stocks", converter = ListToRecyclerAdapterConverter((owner as StockFragment).viewCreator(StockItemView(), ::StockViewModel))) }
                    bind { itemClick("stockDetail") }
                }.lparams(matchParent, matchParent)
            }
        }
    }
}

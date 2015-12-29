package com.benny.app.sample.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benny.app.sample.SampleApplication
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.act
import kotlin.properties.Delegates

import com.benny.library.kbinding.bind.ViewModel
import com.benny.library.kbinding.converter.ListToRecyclerAdapterConverter
import com.benny.library.kbinding.view.ViewBinderComponent
import com.benny.app.sample.model.Stock
import com.benny.app.sample.network.service.caishuo.CaishuoService
import com.benny.app.sample.viewcomponent.StockItemView
import com.benny.app.sample.viewmodel.StockViewModel
import com.benny.library.kbinding.bind.BindingDelegate
import com.benny.library.kbinding.dsl.adapter
import com.benny.library.kbinding.dsl.bind
import org.jetbrains.anko.recyclerview.v7.recyclerView

class StockFragment : BaseFragment() {
    val bindingDelegate = BindingDelegate()
    var contentView: View? = null

    public var stocks: List<Stock>? by bindingDelegate.bindProperty<List<Stock>>("stocks")

    fun fetchStocks() {
        CaishuoService.getInstance().followedStocks("1301").subscribe { stocks = it }
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

    override fun onDestroy() {
        super.onDestroy()
        Log.d("StockFragment", "onDestroy")
        SampleApplication.getRefWatcher(context).watch(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("StockFragment", "onDestroyView")

    }

    inner class StockFragmentUI() : ViewBinderComponent<StockFragment> {
        override fun builder(): AnkoContext<StockFragment>.() -> Unit = {
            relativeLayout() {
                recyclerView {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    bind { adapter(path = "stocks", converter = ListToRecyclerAdapterConverter(owner.viewCreator(StockItemView(), ::StockViewModel))) }
                }.lparams(matchParent, matchParent)
            }
        }
    }


}

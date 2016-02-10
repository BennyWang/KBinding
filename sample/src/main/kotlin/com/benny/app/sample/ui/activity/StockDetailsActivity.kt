package com.benny.app.sample.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import com.benny.app.sample.network.service.caishuo.CaishuoService
import com.benny.app.sample.network.service.caishuo.model.Stock
import com.benny.app.sample.ui.extension.progressBar
import com.benny.app.sample.ui.layout.stock.StockInfoUI
import com.benny.app.sample.viewmodel.StockViewModel
import com.benny.library.kbinding.common.bindings.fadeOut
import com.benny.library.kbinding.common.bindings.until
import com.benny.library.kbinding.dsl.inflate
import com.benny.library.kbinding.dsl.wait
import com.benny.library.kbinding.view.ViewBinderComponent
import com.benny.library.kbinding.view.setContentView
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar

/**
 * Created by benny on 12/30/15.
 */

class StockDetailsActivity : BaseActivity() {
    lateinit var toolBar: Toolbar
    val stockViewModel: StockViewModel = StockViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StockDetailsActivityUI().setContentView(this).bindTo(stockViewModel)

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = ""

        CaishuoService.instance.stock(intent.getStringExtra("id"))
            .subscribe({
                stockViewModel.updateData(it)
            }, {
                Log.e("StockDetailsActivity", "error : $it")
                stockViewModel.updateData(Stock())
            })
    }


    class StockDetailsActivityUI : ViewBinderComponent<StockDetailsActivity> {
        override fun builder(): AnkoContext<*>.() -> Unit = {
            verticalLayout {
                (owner as StockDetailsActivity).toolBar = toolbar {
                    backgroundColor = Color.parseColor("#393a4c")
                }
                frameLayout {
                    relativeLayout {
                        wait { until("stock") { inflate(StockInfoUI(), this@relativeLayout).lparams(matchParent) } }
                    }
                    frameLayout {
                        backgroundColor = Color.WHITE
                        progressBar(android.R.attr.progressBarStyleSmall).lparams { gravity = Gravity.CENTER }
                        wait { until("stock") { fadeOut() } }
                    }
                }.lparams(matchParent, matchParent)
            }
        }
    }
}
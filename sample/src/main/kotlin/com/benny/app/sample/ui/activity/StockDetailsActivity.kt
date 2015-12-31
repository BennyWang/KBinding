package com.benny.app.sample.ui.activity

import android.os.Bundle
import com.benny.app.sample.network.service.caishuo.CaishuoService
import com.benny.app.sample.viewcomponent.stock.StockInfoUI
import com.benny.app.sample.viewmodel.StockViewModel
import com.benny.library.kbinding.dsl.*
import com.benny.library.kbinding.view.ViewBinderComponent
import com.benny.library.kbinding.view.setContentView
import org.jetbrains.anko.*

/**
 * Created by benny on 12/30/15.
 */

class StockDetailsActivity : BaseActivity() {
    val viewModel: StockViewModel = StockViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StockDetailsActivityUI().setContentView(this).bindTo(viewModel)

        CaishuoService.getInstance().stock(intent.getStringExtra("id")).subscribe { viewModel.updateData(it) }
    }

    class StockDetailsActivityUI : ViewBinderComponent<StockDetailsActivity> {
        override fun builder(): AnkoContext<StockDetailsActivity>.() -> Unit = {
            relativeLayout {
                inflate(StockInfoUI(), this@relativeLayout).lparams(matchParent)
            }
        }
    }
}
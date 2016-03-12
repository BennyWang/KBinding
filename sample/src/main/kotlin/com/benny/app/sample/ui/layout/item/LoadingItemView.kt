package com.benny.app.sample.ui.layout.item

import android.graphics.Color
import android.view.Gravity
import com.benny.app.sample.ui.extension.progressBar
import com.benny.library.kbinding.adapterview.viewcreator.ItemViewBinderComponent
import org.jetbrains.anko.*

/**
 * Created by benny on 1/30/16.
 */

class LoadingItemView : ItemViewBinderComponent {
    override fun builder(): AnkoContext<*>.() -> Unit = {
        relativeLayout {
            frameLayout {
                progressBar (android.R.attr.progressBarStyleSmall) {
                    isIndeterminate = true
                }.lparams(width = dip(24), height = dip(24)) { gravity = Gravity.CENTER }
            }.lparams(matchParent, wrapContent)
        }
    }
}
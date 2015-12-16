package com.benny.app.sample.viewcomponent

import android.app.Activity
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewManager
import org.jetbrains.anko.*

import com.benny.library.neobinding.extension.*
import com.benny.library.neobinding.view.ViewBinderComponent

/**
 * Created by benny on 12/16/15.
 */

class LoadingItemViewComponent : ViewBinderComponent<Activity> {
    override fun builder(): AnkoContext<*>.() -> Unit = {
        frameLayout {
            progressBar (android.R.attr.progressBarStyle) {
                isIndeterminate = true
            }.lparams(width = dip(24), height = dip(24)) { gravity = Gravity.CENTER }
        }.layoutParams = ViewGroup.LayoutParams(matchParent, dip(50))
    }
}



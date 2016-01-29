package com.benny.app.sample.viewcomponent

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import com.benny.library.kbinding.view.ViewBinderComponent
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar

/**
 * Created by benny on 1/15/16.
 */

class TitleToolBarViewComponent(val title: String) : ViewBinderComponent<Activity> {
    override fun builder(): AnkoContext<*>.() -> Unit = {
        toolbar {
            backgroundColor = Color.parseColor("#393a4c")
            textView {
                text = title
                textSize = 16f
                textColor = Color.WHITE
            }.lparams(wrapContent, wrapContent) { gravity = Gravity.CENTER }
        }
    }
}
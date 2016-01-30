package com.benny.app.sample.ui.extension

import android.content.Context
import android.view.View
import android.view.ViewManager
import android.widget.ProgressBar

import com.facebook.drawee.view.SimpleDraweeView

import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.custom.ankoView

import com.benny.app.sample.ui.widget.ViewPagerIndicator

/**
 * Created by benny on 12/21/15.
 */

var View.backgroundColorResource: Int
    get() = throw UnsupportedOperationException()
    set(value) { this.backgroundColor = context.resources.getColor(value) }

fun ViewManager.simpleDraweeView(init: SimpleDraweeView.() -> Unit): SimpleDraweeView {
    return ankoView({ctx: Context -> SimpleDraweeView(ctx)}) { init() }
}

fun ViewManager.viewPagerIndicator(init: ViewPagerIndicator.() -> Unit): ViewPagerIndicator {
    return ankoView({ctx: Context -> ViewPagerIndicator(ctx)}) { init() }
}

fun ViewManager.progressBar(style: Int): ProgressBar {
    return ankoView({ctx: Context -> ProgressBar(ctx, null, style) }) { }
}
fun ViewManager.progressBar(style: Int, init: ProgressBar.() -> Unit): ProgressBar {
    return ankoView({ctx: Context -> ProgressBar(ctx, null, style) }) { init() }
}

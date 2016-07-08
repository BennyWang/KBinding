package com.benny.app.sample.ui.extension

import android.content.Context
import android.support.v7.view.ContextThemeWrapper
import android.view.View
import android.view.ViewManager
import android.widget.ProgressBar
import com.benny.app.sample.ui.widget.ViewPagerIndicator
import com.facebook.drawee.view.SimpleDraweeView
import org.jetbrains.anko.appcompat.v7._Toolbar
import org.jetbrains.anko.appcompat.v7.`$$Anko$Factories$AppcompatV7ViewGroup`
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.internals.AnkoInternals

/**
 * Created by benny on 12/21/15.
 */

var View.backgroundColorResource: Int
    get() = throw UnsupportedOperationException()
    set(value) { this.backgroundColor = context.resources.getColor(value) }

fun ViewManager.simpleDraweeView(theme: Int = 0, init: SimpleDraweeView.() -> Unit): SimpleDraweeView {
    return ankoView({ctx: Context -> SimpleDraweeView(ctx)}, theme) { init() }
}

fun ViewManager.viewPagerIndicator(theme: Int = 0, init: ViewPagerIndicator.() -> Unit): ViewPagerIndicator {
    return ankoView({ctx: Context -> ViewPagerIndicator(ctx)}, theme) { init() }
}

fun ViewManager.progressBar(theme: Int = 0, style: Int): ProgressBar {
    return ankoView({ctx: Context -> ProgressBar(ctx, null, style) }, theme) { }
}
fun ViewManager.progressBar(theme: Int = 0, style: Int, init: ProgressBar.() -> Unit): ProgressBar {
    return ankoView({ctx: Context -> ProgressBar(ctx, null, style) }, theme) { init() }
}

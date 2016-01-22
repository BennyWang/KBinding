package com.benny.app.sample.ui.extension

import android.content.Context
import android.view.View
import android.view.ViewManager
import com.benny.app.sample.ui.widget.ViewPagerIndicator
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.custom.ankoView

/**
 * Created by benny on 12/21/15.
 */

public fun ViewManager.viewPagerIndicator(init: ViewPagerIndicator.() -> Unit): ViewPagerIndicator {
    return ankoView({ctx: Context -> ViewPagerIndicator(ctx)}) { init() }
}

public var View.backgroundColorResource: Int
    get() = throw UnsupportedOperationException()
    set(value) { this.backgroundColor = context.resources.getColor(value) }
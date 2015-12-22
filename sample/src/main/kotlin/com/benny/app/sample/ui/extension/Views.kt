package com.benny.app.sample.ui.extension

import android.content.Context
import android.view.ViewManager
import com.benny.app.sample.ui.widget.ViewPagerIndicator
import org.jetbrains.anko.custom.ankoView

/**
 * Created by benny on 12/21/15.
 */

public fun ViewManager.viewPagerIndicator(init: ViewPagerIndicator.() -> Unit): ViewPagerIndicator {
    return ankoView({ctx: Context -> ViewPagerIndicator(ctx)}) { init() }
}

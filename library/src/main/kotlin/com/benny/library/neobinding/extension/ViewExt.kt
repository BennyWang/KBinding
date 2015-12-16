package com.benny.library.neobinding.extension

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import com.benny.library.neobinding.bind.PropertyBinding
import com.benny.library.neobinding.view.BindableLayout
import com.benny.library.neobinding.view.ViewComponent
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.internals.AnkoInternals

/**
 * Created by benny on 12/16/15.
 */

fun AnkoContext<*>.bind(propertyBindingFactory: () -> PropertyBinding): Unit {
    if(this is BindableLayout) bindingAssembler.addBinding(propertyBindingFactory())
}

public fun AnkoContext<*>.inflate(viewComponent: ViewComponent, parent: ViewGroup, prefix: String = "") : View = when(this) {
    is BindableLayout -> {
        val layout = ctx.bindableLayout { viewComponent.builder()() }
        merge(prefix, layout)
        parent.addView(layout.view)
        layout.view
    }
    else -> {
        val view = ctx.UI { viewComponent.builder()() }.view
        parent.addView(view)
        view
    }
}


public fun Context.bindableLayout(init: BindableLayout.() -> Unit): BindableLayout {
    val bindableLayout = BindableLayout(this)
    bindableLayout.init()
    AnkoInternals.addView(this, bindableLayout.view)
    return bindableLayout
}
public fun AnkoContext<*>.bindableLayout(init: BindableLayout.() -> Unit): BindableLayout {
    val bindableLayout = BindableLayout(this.ctx)
    bindableLayout.init()
    AnkoInternals.addView(this, bindableLayout.view)
    return bindableLayout
}


public fun ViewManager.progressBar(style: Int, init: android.widget.ProgressBar.() -> Unit): android.widget.ProgressBar {
    return ankoView({ctx: Context -> android.widget.ProgressBar(ctx, null, style)}) { init() }
}

public fun ViewManager.dip(value: Int): Int = when(this) {
    is ViewGroup -> context.dip(value)
    else -> value
}

public fun ViewManager.dip(value: Float): Int = when(this) {
    is ViewGroup -> context.dip(value)
    else -> value.toInt()
}

public fun ViewManager.sp(value: Int): Int = when(this) {
    is ViewGroup -> context.sp(value)
    else -> value
}
public fun ViewManager.sp(value: Float): Int = when(this) {
    is ViewGroup -> context.sp(value)
    else -> value.toInt()
}

public fun ViewManager.px2dip(px: Int): Float = when(this) {
    is ViewGroup -> context.px2dip(px)
    else -> px.toFloat()
}
public fun ViewManager.px2sp(px: Int): Float = when(this) {
    is ViewGroup -> context.px2sp(px)
    else -> px.toFloat()
}
public fun ViewManager.dimen(resource: Int): Int = when(this) {
    is ViewGroup -> context.dimen(resource)
    else -> throw UnsupportedOperationException()
}
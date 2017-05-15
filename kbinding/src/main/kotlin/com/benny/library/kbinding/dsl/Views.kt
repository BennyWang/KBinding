package com.benny.library.kbinding.dsl

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.view.BindableLayout
import com.benny.library.kbinding.view.ViewComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.UI
import org.jetbrains.anko.internals.AnkoInternals

/**
 * Created by benny on 12/16/15.
 */
val AnkoContext<*>.OneWay: OneWay get() = BindingMode.OneWay
val AnkoContext<*>.TwoWay: TwoWay get() = BindingMode.TwoWay
val AnkoContext<*>.OneWayToSource: OneWayToSource get() = BindingMode.OneWayToSource

inline fun <T> AnkoContext<T>.bindableLayout(init: BindableLayout<T>.() -> Unit): BindableLayout<T> {
    val bindableLayout = BindableLayout(this.ctx, this.owner)
    bindableLayout.init()
    AnkoInternals.addView(this, bindableLayout.view)
    return bindableLayout
}

inline fun AnkoContext<*>.bind(propertyBindingFactory: () -> PropertyBinding): Unit {
    if(this is BindableLayout<*>) bindingAssembler.addBinding(propertyBindingFactory())
}

inline fun AnkoContext<*>.wait(propertyBindingFactory: () -> PropertyBinding): Unit {
    bind(propertyBindingFactory)
}

fun <T> AnkoContext<T>.inflate(viewComponent: ViewComponent<T>, parent: ViewGroup, prefix: String = "") : View = when(this) {
    is BindableLayout<T> -> {
        inflate(viewComponent, parent, prefix)
    }
    else -> {
        val view = ctx.UI {
            val builder: AnkoContext<Context>.() -> Unit = viewComponent.builder() as AnkoContext<Context>.() -> Unit
            this.builder()
        }.view
        parent.addView(view)
        view
    }
}

fun AnkoContext<*>.resolveAttribute(attrId: Int): Int {
    val outValue: TypedValue = TypedValue();
    ctx.theme.resolveAttribute(attrId, outValue, true)
    return outValue.resourceId
}
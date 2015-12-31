package com.benny.library.kbinding.dsl

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.view.BindableLayout
import com.benny.library.kbinding.view.ViewComponent
import org.jetbrains.anko.*
import org.jetbrains.anko.internals.AnkoInternals

/**
 * Created by benny on 12/16/15.
 */
public val AnkoContext<*>.OneWay: OneWay get() = BindingMode.OneWay
public val AnkoContext<*>.TwoWay: TwoWay get() = BindingMode.TwoWay
public val AnkoContext<*>.OneWayToSource: OneWayToSource get() = BindingMode.OneWayToSource

public fun <T> AnkoContext<T>.bindableLayout(init: BindableLayout<T>.() -> Unit): BindableLayout<T> {
    val bindableLayout = BindableLayout(this.ctx, this.owner)
    bindableLayout.init()
    AnkoInternals.addView(this, bindableLayout.view)
    return bindableLayout
}

fun AnkoContext<*>.bind(propertyBindingFactory: () -> PropertyBinding): Unit {
    if(this is BindableLayout) bindingAssembler.addBinding(propertyBindingFactory())
}

public fun <T> AnkoContext<T>.inflate(viewComponent: ViewComponent<*>, parent: ViewGroup, prefix: String = "") : View = when(this) {
    is BindableLayout -> {
        inflate(viewComponent, parent, prefix)
    }
    else -> {
        val view = ctx.UI { viewComponent.builder()() }.view
        parent.addView(view)
        view
    }
}

public var TextView.textWeight: Int
    get() = throw AnkoException("'android.widget.TextView.textWeight' property does not have a getter")
    set(v) = setTypeface(typeface, v)

public var TextView.textColorResource: Int
    get() = throw AnkoException("'android.widget.TextView.textWeight' property does not have a getter")
    set(v) = setTextColor(context.resources.getColor(v))

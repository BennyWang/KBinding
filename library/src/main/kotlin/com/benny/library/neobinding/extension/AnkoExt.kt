package com.benny.library.neobinding.extension

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import com.benny.library.neobinding.adapter.Swappable
import com.benny.library.neobinding.bind.PropertyBinding
import com.benny.library.neobinding.view.BindableLayout
import com.benny.library.neobinding.view.ViewComponent
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.internals.AnkoInternals
import rx.functions.Action1

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

public var TextView.textColorResource: Int
    get() = throw AnkoException("'textColorResource' property does not have a getter")
    set(v) = setTextColor(context.resources.getColor(v))

public fun ViewManager.progressBar(style: Int, init: android.widget.ProgressBar.() -> Unit): android.widget.ProgressBar {
    return ankoView({ctx: Context -> android.widget.ProgressBar(ctx, null, style)}) { init() }
}

public var android.widget.TextView.textStyle: Int
    get() = throw AnkoException("'android.widget.TextView.textStyle' property does not have a getter")
    set(v) = setTypeface(typeface, v)

public fun RecyclerView.swapAdapter() : Action1<RecyclerView.Adapter<RecyclerView.ViewHolder>> {
    return Action1 { it -> this.swapAdapter(it, false) }
}


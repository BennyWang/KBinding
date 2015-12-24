package com.benny.library.kbinding.extension

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.ProgressBar
import com.benny.library.kbinding.bind.PropertyBinding
import com.benny.library.kbinding.view.BindableLayout
import com.benny.library.kbinding.view.ViewComponent
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.internals.AnkoInternals

/**
 * Created by benny on 12/16/15.
 */

fun AnkoContext<*>.bind(propertyBindingFactory: () -> PropertyBinding): Unit {
    if(this is BindableLayout) bindingAssembler.addBinding(propertyBindingFactory())
}

public fun <T> AnkoContext<T>.inflate(viewComponent: ViewComponent<*>, parent: ViewGroup, prefix: String = "") : View = when(this) {
    is BindableLayout -> {
        val layout = this.bindableLayout { viewComponent.builder()() }
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

public fun <T> AnkoContext<T>.bindableLayout(init: BindableLayout<T>.() -> Unit): BindableLayout<T> {
    val bindableLayout = BindableLayout(this.ctx, this.owner)
    bindableLayout.init()
    AnkoInternals.addView(this, bindableLayout.view)
    return bindableLayout
}

public fun ViewManager.recyclerView(init: RecyclerView.() -> Unit): RecyclerView {
    val recyclerViewFactory: (Context) -> RecyclerView = { ctx ->
        val recyclerView: RecyclerView = RecyclerView(ctx)
        recyclerView.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        recyclerView
    }
    return ankoView(recyclerViewFactory, init)
}

public fun ViewManager.progressBar(style: Int, init: ProgressBar.() -> Unit): ProgressBar {
    return ankoView({ctx: Context -> ProgressBar(ctx, null, style) }) { init() }
}
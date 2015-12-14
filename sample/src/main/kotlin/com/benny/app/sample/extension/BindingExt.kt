package com.benny.app.sample.extension

import android.app.Activity
import android.view.View
import com.benny.library.neobinding.kotlin.bind.BindingContext
import com.benny.library.neobinding.kotlin.bind.ViewModel
import com.benny.library.neobinding.kotlin.view.BindableLayout
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.custom.ankoView

/**
 * Created by benny on 12/14/15.
 */

public fun View.bindTo(bindingContext: BindingContext<*>, viewModel: ViewModel<*>): Unit = when(this) {
    is BindableLayout -> bindTo(bindingContext, viewModel)
    else -> {}
}

public fun Activity.bindableLayout(init: BindableLayout.() -> Unit): BindableLayout {
    return ankoView({ BindableLayout(this) }, init)
}

public fun AnkoContext<*>.bindableLayout(init: BindableLayout.() -> Unit): BindableLayout {
    return ankoView({ BindableLayout(this.ctx) }, init)
}
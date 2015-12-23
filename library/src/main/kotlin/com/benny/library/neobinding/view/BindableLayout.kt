package com.benny.library.neobinding.view

import android.content.Context
import android.net.IpPrefix
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoContext

import com.benny.library.neobinding.bind.*
/**
 * Created by benny on 12/12/15.
 */

public class BindableLayout<T>(override val ctx: Context, override val owner: T) : AnkoContext<T>, ViewBinder {
    var childView: View? = null

    override val view: View
        get() = childView ?: throw IllegalStateException("View was not set previously")

    val bindingAssembler = BindingAssembler()

    public override fun bindTo(bindingDisposer: BindingDisposer, viewModel: ViewModel): View {
        bindingAssembler.bindTo(bindingDisposer, viewModel)
        return view
    }

    public fun bind(propertyBinding: PropertyBinding): Unit {
        bindingAssembler.addBinding(propertyBinding)
    }

    public fun merge(prefix: String, layout: BindableLayout<*>) {
        bindingAssembler.merge(prefix, layout.bindingAssembler)
    }

    override fun addView(view: View?, params: ViewGroup.LayoutParams?) {
        if(view == null) return

        if(this.childView != null) throw UnsupportedOperationException("BindableLayout can only have one child")
        this.childView = view
    }
}


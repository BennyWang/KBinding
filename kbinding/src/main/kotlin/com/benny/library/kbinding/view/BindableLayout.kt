package com.benny.library.kbinding.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.benny.library.kbinding.bind.BindingAssembler
import com.benny.library.kbinding.bind.BindingDisposer
import com.benny.library.kbinding.bind.PropertyBinding
import com.benny.library.kbinding.bind.ViewModel
import org.jetbrains.anko.AnkoContext

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


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

public class BindableLayout(override val ctx: Context) : AnkoContext<Unit>, ViewBinder {
    var childView: View? = null

    override val owner: Unit
        get() = throw UnsupportedOperationException()

    override val view: View
        get() = childView ?: throw IllegalStateException("View was not set previously")

    val bindingAssembler = BindingAssembler()

    public override fun bindTo(bindingContext: BindingContext, viewModel: ViewModel): View {
        bindingAssembler.bindTo(bindingContext, viewModel)
        return view
    }

    public fun bind(propertyBinding: PropertyBinding): Unit {
        bindingAssembler.addBinding(propertyBinding)
    }

    public fun merge(prefix: String, layout: BindableLayout) {
        bindingAssembler.merge(prefix, layout.bindingAssembler)
    }

    override fun addView(view: View?, params: ViewGroup.LayoutParams?) {
        if(view == null) return

        if(this.childView != null) throw UnsupportedOperationException("BindableLayout can only have one child")
        this.childView = view
    }
}


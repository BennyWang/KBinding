package com.benny.library.kbinding.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.benny.library.kbinding.bind.BindingAssembler
import com.benny.library.kbinding.bind.BindingDisposer
import com.benny.library.kbinding.bind.IViewModel
import com.benny.library.kbinding.bind.PropertyBinding
import com.benny.library.kbinding.dsl.bindableLayout
import org.jetbrains.anko.AnkoContext

/**
 * Created by benny on 12/12/15.
 */

class BindableLayout<T>(override val ctx: Context, override val owner: T) : AnkoContext<T>, ViewBinder {
    var childView: View? = null
    var bindingDisposer: BindingDisposer? = null
    var viewModel: IViewModel? = null

    override val view: View
        get() = childView ?: throw IllegalStateException("View was not set previously")

    val bindingAssembler = BindingAssembler()

    override fun bindTo(bindingDisposer: BindingDisposer, viewModel: IViewModel): View {
        if(this.viewModel != null) throw UnsupportedOperationException("can only bind to one view model")

        this.bindingDisposer = bindingDisposer
        this.viewModel = viewModel
        bindingAssembler.bindTo(bindingDisposer, viewModel)
        return view
    }

    fun bind(propertyBinding: PropertyBinding): Unit {
        bindingAssembler.addBinding(propertyBinding)
    }

    fun inflate(viewComponent: ViewComponent, parent: ViewGroup, prefix: String = "") : View {
        val layout = AnkoContext.create(ctx, owner).bindableLayout { viewComponent.builder()() }
        merge(prefix, layout)
        parent.addView(layout.view)
        return layout.view
    }

    fun merge(prefix: String, layout: BindableLayout<*>) {
        bindingAssembler.merge(prefix, layout.bindingAssembler)
        if(viewModel != null) bindingAssembler.bindTo(bindingDisposer!!, viewModel!!)
    }

    override fun addView(view: View?, params: ViewGroup.LayoutParams?) {
        if(view == null) return

        if(this.childView != null) throw UnsupportedOperationException("BindableLayout can only have one child")
        this.childView = view
    }
}


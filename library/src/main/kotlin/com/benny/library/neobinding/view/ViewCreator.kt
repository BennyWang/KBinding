package com.benny.library.neobinding.view

import android.view.View
import android.view.ViewGroup
import com.benny.library.neobinding.bind.BindingContext
import com.benny.library.neobinding.bind.ViewModel

/**
 * Created by benny on 11/18/15.
 */

public open class ViewCreator<T>(val bindingContext: BindingContext<*>, val viewComponent: ViewComponent, val viewModelFactory: () -> ViewModel<T>) : IViewCreator<T> {

    override fun view(container: ViewGroup): View {
        val viewBinder = viewComponent.create(bindingContext.context)
        val viewModel = viewModelFactory()
        viewBinder.bindTo(bindingContext, viewModel)
        viewBinder.contentView.tag = viewModel
        return viewBinder.contentView
    }

    override fun viewTypeFor(data: T?, position: Int): Int {
        return  0
    }

    override fun viewTypeCount(): Int {
        return 1
    }
}
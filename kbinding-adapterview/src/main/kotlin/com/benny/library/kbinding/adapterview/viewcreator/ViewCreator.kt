package com.benny.library.kbinding.adapterview.viewcreator

import android.view.View
import android.view.ViewGroup
import com.benny.library.autoadapter.viewcreator.IViewCreator
import com.benny.library.kbinding.adapterview.viewcreator.ItemViewBinderComponent
import com.benny.library.kbinding.bind.BindingDisposer
import com.benny.library.kbinding.adapterview.viewmodel.ItemViewModel
import com.benny.library.kbinding.view.BindingDisposerGenerator

/**
 * Created by benny on 3/12/16.
 */

open class ViewCreator(val bindingDisposer: BindingDisposer, val itemViewBinderComponent: ItemViewBinderComponent, val viewModelFactory: () -> ItemViewModel<*>) : IViewCreator<Any> {

    override fun view(container: ViewGroup): View {
        val viewBinder = itemViewBinderComponent.createViewBinder(container.context, container)
        val viewModel = viewModelFactory()
        viewBinder.bindTo(bindingDisposer, viewModel)
        viewBinder.view.tag = viewModel
        return viewBinder.view
    }

    override fun viewTypeFor(data: Any?, position: Int, itemCount: Int): Int {
        return  0
    }

    override fun viewTypeCount(): Int {
        return 1
    }
}

fun BindingDisposerGenerator.viewCreator(viewBinderComponent: ItemViewBinderComponent, viewModelFactory: () -> ItemViewModel<*>): ViewCreator = ViewCreator(bindingDisposer, viewBinderComponent, viewModelFactory)

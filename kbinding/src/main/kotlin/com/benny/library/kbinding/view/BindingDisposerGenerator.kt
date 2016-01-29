package com.benny.library.kbinding.view

import com.benny.library.kbinding.bind.BindingDisposer
import com.benny.library.kbinding.bind.IViewModel
import com.benny.library.kbinding.bind.ItemViewModel
import com.benny.library.kbinding.bind.ViewModel

/**
 * Created by benny on 12/23/15.
 */
interface BindingDisposerGenerator {
    val bindingDisposer: BindingDisposer

    fun viewCreator(viewBinderComponent: ItemViewBinderComponent, viewModelFactory: () -> ItemViewModel<*>): ViewCreator = ViewCreator(bindingDisposer, viewBinderComponent, viewModelFactory)
    fun ViewBinder.bindTo(viewModel: IViewModel) = this.bindTo(bindingDisposer, viewModel)

}
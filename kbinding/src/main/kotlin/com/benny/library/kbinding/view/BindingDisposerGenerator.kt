package com.benny.library.kbinding.view

import com.benny.library.kbinding.bind.BindingDelegate
import com.benny.library.kbinding.bind.BindingDisposer
import com.benny.library.kbinding.viewmodel.IViewModel
import com.benny.library.kbinding.viewmodel.ItemViewModel

/**
 * Created by benny on 12/23/15.
 */
interface BindingDisposerGenerator {
    val bindingDisposer: BindingDisposer

    fun viewCreator(viewBinderComponent: ItemViewBinderComponent, viewModelFactory: () -> ItemViewModel<*>): ViewCreator = ViewCreator(bindingDisposer, viewBinderComponent, viewModelFactory)
    fun pagingViewCreator(loadingViewBinderComponent: ItemViewBinderComponent, viewBinderComponent: ItemViewBinderComponent, viewModelFactory: () -> ItemViewModel<*>): PagingViewCreator = PagingViewCreator(bindingDisposer, loadingViewBinderComponent, viewBinderComponent, viewModelFactory)
    fun ViewBinder.bindTo(viewModel: IViewModel) = this.bindTo(bindingDisposer, viewModel)
    fun ViewBinder.bindTo(bindingDelegate: BindingDelegate) = this.bindTo(bindingDisposer, bindingDelegate)
}
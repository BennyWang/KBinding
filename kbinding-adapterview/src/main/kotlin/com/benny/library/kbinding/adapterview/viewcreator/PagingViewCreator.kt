package com.benny.library.kbinding.adapterview.viewcreator

import com.benny.library.autoadapter.viewcreator.ViewCreatorCollection
import com.benny.library.kbinding.adapterview.viewcreator.ItemViewBinderComponent
import com.benny.library.kbinding.bind.BindingDisposer
import com.benny.library.kbinding.adapterview.viewcreator.ViewCreator
import com.benny.library.kbinding.adapterview.viewmodel.ItemViewModel
import com.benny.library.kbinding.view.BindingDisposerGenerator

/**
 * Created by benny on 3/12/16.
 */

class PagingViewCreator(val bindingDisposer: BindingDisposer, val loadingViewBinderComponent: ItemViewBinderComponent, val itemViewBinderComponent: ItemViewBinderComponent, val viewModelFactory: () -> ItemViewModel<*>) : ViewCreatorCollection<Any>() {
    init {
        addFilter({ data, position, itemCount -> data != null }, ViewCreator(bindingDisposer, itemViewBinderComponent, viewModelFactory))
        addFilter({ data, position, itemCount -> data == null }, ViewCreator(bindingDisposer, loadingViewBinderComponent, viewModelFactory))
    }
}

fun BindingDisposerGenerator.pagingViewCreator(loadingViewBinderComponent: ItemViewBinderComponent, viewBinderComponent: ItemViewBinderComponent, viewModelFactory: () -> ItemViewModel<*>): PagingViewCreator = PagingViewCreator(bindingDisposer, loadingViewBinderComponent, viewBinderComponent, viewModelFactory)

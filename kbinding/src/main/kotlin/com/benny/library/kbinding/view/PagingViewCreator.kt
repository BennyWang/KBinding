package com.benny.library.kbinding.view

import com.benny.library.kbinding.bind.BindingDisposer
import com.benny.library.kbinding.bind.ItemViewModel
import rx.functions.Func2
import rx.functions.Func3

/**
 * Created by benny on 11/18/15.
 */

class PagingViewCreator(val bindingDisposer: BindingDisposer, val loadingViewBinderComponent: ItemViewBinderComponent, val itemViewBinderComponent: ItemViewBinderComponent, val viewModelFactory: () -> ItemViewModel<*>) : ViewCreatorCollection<Any>() {
    init {
        add(Func3 { data, position, itemCount -> data != null }, ViewCreator(bindingDisposer, itemViewBinderComponent, viewModelFactory))
        add(Func3 { data, position, itemCount -> data == null }, ViewCreator(bindingDisposer, loadingViewBinderComponent, viewModelFactory))
    }
}
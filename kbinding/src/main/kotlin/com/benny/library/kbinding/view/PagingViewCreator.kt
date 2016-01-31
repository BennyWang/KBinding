package com.benny.library.kbinding.view

import android.view.View
import android.view.ViewGroup
import com.benny.library.kbinding.bind.BindingDisposer
import com.benny.library.kbinding.bind.ItemViewModel
import rx.functions.Func2
import java.util.*

/**
 * Created by benny on 11/18/15.
 */

class PagingViewCreator(val bindingDisposer: BindingDisposer, val loadingViewBinderComponent: ItemViewBinderComponent, val itemViewBinderComponent: ItemViewBinderComponent, val viewModelFactory: () -> ItemViewModel<*>) : ViewCreatorCollection<Any>() {
    init {
        add(Func2 { data, position -> data != null }, ViewCreator(bindingDisposer, itemViewBinderComponent, viewModelFactory))
        add(Func2 { data, position -> data == null }, ViewCreator(bindingDisposer, loadingViewBinderComponent, viewModelFactory))
    }
}
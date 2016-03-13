package com.benny.library.kbinding.view

import com.benny.library.kbinding.bind.BindingDelegate
import com.benny.library.kbinding.bind.BindingDisposer
import com.benny.library.kbinding.viewmodel.IViewModel

/**
 * Created by benny on 12/23/15.
 */

interface BindingDisposerGenerator {
    val bindingDisposer: BindingDisposer

    fun ViewBinder.bindTo(viewModel: IViewModel) = this.bindTo(bindingDisposer, viewModel)
    fun ViewBinder.bindTo(bindingDelegate: BindingDelegate) = this.bindTo(bindingDisposer, bindingDelegate)
}
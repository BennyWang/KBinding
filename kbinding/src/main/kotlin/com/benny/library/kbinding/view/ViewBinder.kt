package com.benny.library.kbinding.view

import android.view.View
import com.benny.library.kbinding.bind.BindingDisposer
import com.benny.library.kbinding.bind.ViewModel

/**
 * Created by benny on 12/14/15.
 */

interface ViewBinder {
    val view: View
    fun bindTo(bindingDisposer: BindingDisposer, viewModel: ViewModel): View
}
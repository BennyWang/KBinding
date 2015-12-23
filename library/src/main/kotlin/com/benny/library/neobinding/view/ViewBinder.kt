package com.benny.library.neobinding.view

import android.view.View
import com.benny.library.neobinding.bind.BindingDisposer
import com.benny.library.neobinding.bind.ViewModel

/**
 * Created by benny on 12/14/15.
 */

interface ViewBinder {
    val view: View
    fun bindTo(bindingDisposer: BindingDisposer, viewModel: ViewModel): View
}
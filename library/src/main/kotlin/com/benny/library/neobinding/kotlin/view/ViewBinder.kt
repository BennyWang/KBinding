package com.benny.library.neobinding.kotlin.view

import android.view.View
import com.benny.library.neobinding.kotlin.bind.BindingContext
import com.benny.library.neobinding.kotlin.bind.ViewModel

/**
 * Created by benny on 12/14/15.
 */

interface ViewBinder {
    val contentView: View
    fun bindTo(bindingContext: BindingContext<*>, viewModel: ViewModel<*>)
}
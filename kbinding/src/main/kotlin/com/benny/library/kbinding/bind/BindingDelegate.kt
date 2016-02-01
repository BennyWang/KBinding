package com.benny.library.kbinding.bind;

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty

/**
 * Created by benny on 11/17/15.
 */

interface BindingDelegate {
    val viewModel: ViewModel

    fun <T> bindProperty(key: String): ReadWriteProperty<Any, T?> = viewModel.bindProperty(key)

    fun <T> bindProperty(vararg keys: String, getter: () -> T): ReadWriteProperty<Any, T> = viewModel.bindProperty(*keys, getter = getter)

    fun <T> bindCommand(key: String, cmdAction: (T, (Boolean) -> Unit) -> Unit): ReadOnlyProperty<Any, Command<T>> = viewModel.bindCommand(key, cmdAction)
}


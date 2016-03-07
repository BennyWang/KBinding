package com.benny.library.kbinding.bind

import com.benny.library.kbinding.viewmodel.Command
import com.benny.library.kbinding.viewmodel.ViewModel
import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by benny on 11/17/15.
 */

interface BindingDelegate {
    val viewModel: ViewModel

    fun <T> bindPropertyV2(key: String, initialValue: T? = null) = viewModel.bindPropertyV2(key, initialValue)
    fun <T> bindPropertyV2(key: String, dependsOn: Array<String>, getter: () -> T) = viewModel.bindPropertyV2(key, dependsOn, getter)
    fun <T> bindCommandV2(key: String, command: Command<T>) = viewModel.bindCommandV2(key, command)

    fun <T> Delegates.property(): ReadWriteProperty<Any, T?> = with(viewModel) {
        return Delegates.property()
    }

    fun <T> Delegates.property(initialValue: T): ReadWriteProperty<Any, T> = with(viewModel) {
        return Delegates.property(initialValue)
    }

    fun <T> Delegates.property(getter: () -> T): ReadOnlyProperty<Any, T> = with(viewModel) {
        return Delegates.property(getter)
    }
}


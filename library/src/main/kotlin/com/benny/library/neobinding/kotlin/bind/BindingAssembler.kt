package com.benny.library.neobinding.kotlin.bind

import android.os.Build
import android.view.View
import com.benny.library.neobinding.kotlin.view.ViewFinder
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by benny on 11/18/15.
 */

open public class BindingAssembler {
    private val oneWayPropertyBindings = ArrayList<OneWayPropertyBinding<*, *>>()
    private val multiplePropertyBindings = ArrayList<OneWayPropertyBinding<*, *>>()
    private val twoWayPropertyBindings = ArrayList<TwoWayPropertyBinding<*, *>>()
    private var commandBindings = ArrayList<CommandBinding>()

    fun oneWayPropertyBindings(): List<OneWayPropertyBinding<*, *>> {
        return oneWayPropertyBindings
    }

    fun twoWayPropertyBindings(): List<TwoWayPropertyBinding<*, *>> {
        return twoWayPropertyBindings
    }

    fun multiplePropertyBindings(): List<OneWayPropertyBinding<*, *>> {
        return multiplePropertyBindings
    }

    fun commandBindings(): List<CommandBinding> {
        return commandBindings
    }

    public fun addOneWayPropertyBinding(oneWayPropertyBinding: OneWayPropertyBinding<*, *>) {
        oneWayPropertyBindings.add(oneWayPropertyBinding)
    }

    public fun addTwoWayPropertyBinding(twoWayPropertyBinding: TwoWayPropertyBinding<*, *>) {
        twoWayPropertyBindings.add(twoWayPropertyBinding)
    }

    public fun addMultiplePropertyBinding(multiplePropertyBinding: OneWayPropertyBinding<*, *>) {
        multiplePropertyBindings.add(multiplePropertyBinding)
    }

    public fun addCommandBinding(commandBinding: CommandBinding) {
        commandBindings.add(commandBinding)
    }

    public fun bindTo(bindingContext: BindingContext<*>, viewModel: ViewModel<*>) {
        oneWayPropertyBindings().forEach { propertyBinding -> viewModel.bindProperty(bindingContext, propertyBinding) }
        twoWayPropertyBindings().forEach { propertyBinding -> viewModel.bindProperty(bindingContext, propertyBinding) }
        multiplePropertyBindings().forEach { propertyBinding -> viewModel.bindProperties(bindingContext, propertyBinding) }
        commandBindings().forEach { commandBinding -> viewModel.bindCommand(bindingContext, commandBinding) }
    }
}
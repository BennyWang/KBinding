package com.benny.library.neobinding.kotlin.bind

import android.view.View
import java.util.*

/**
 * Created by benny on 11/18/15.
 */

public abstract class BindableView {
    private val oneWayPropertyBindings = ArrayList<OneWayPropertyBinding<*, *>>()
    private val multiplePropertyBindings = ArrayList<OneWayPropertyBinding<*, *>>()
    private val twoWayPropertyBindings = ArrayList<TwoWayPropertyBinding<*, *>>()
    private var commandBindings = ArrayList<CommandBinding<*>>()

    fun oneWayPropertyBindings(): List<OneWayPropertyBinding<*, *>> {
        return oneWayPropertyBindings
    }

    fun twoWayPropertyBindings(): List<TwoWayPropertyBinding<*, *>> {
        return twoWayPropertyBindings
    }

    fun multiplePropertyBindings(): List<OneWayPropertyBinding<*, *>> {
        return multiplePropertyBindings
    }

    fun commandBindings(): List<CommandBinding<*>> {
        return commandBindings
    }

    protected fun addOneWayPropertyBinding(oneWayPropertyBinding: OneWayPropertyBinding<*, *>) {
        oneWayPropertyBindings.add(oneWayPropertyBinding)
    }

    protected fun addTwoWayPropertyBinding(twoWayPropertyBinding: TwoWayPropertyBinding<*, *>) {
        twoWayPropertyBindings.add(twoWayPropertyBinding)
    }

    protected fun addMultiplePropertyBinding(multiplePropertyBinding: OneWayPropertyBinding<*, *>) {
        multiplePropertyBindings.add(multiplePropertyBinding)
    }

    protected fun addCommandBinding(commandBinding: CommandBinding<*>) {
        commandBindings.add(commandBinding)
    }

    abstract fun inject(bindingContext: BindingContext<*>, view: View)
}
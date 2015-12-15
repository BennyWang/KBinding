package com.benny.library.neobinding.bind

import com.benny.library.neobinding.converter.*
import rx.Observable
import rx.functions.Action1
import java.util.*

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

    public fun <T, R> addOneWayPropertyBinding(key: String, observable: Observable<T>, converter: OneWayConverter<R> = EmptyOneWayConverter<R>()) {
        oneWayPropertyBindings.add(OneWayPropertyBinding(key, observable, converter))
    }

    public fun <T> addOneWayPropertyBinding(key: String, observer: Action1<in T>, backConverter: OneWayConverter<T> = EmptyOneWayConverter<T>()) {
        oneWayPropertyBindings.add(OneWayPropertyBinding<T, Any>(key, observer, backConverter))
    }

    public fun <T> addMultiplePropertyBinding(keys: List<String>, observer: Action1<in T>, multipleConverter: MultipleConverter<T>) {
        multiplePropertyBindings.add(OneWayPropertyBinding<T, Any>(keys, observer, multipleConverter))
    }

    public fun addTwoWayPropertyBinding(twoWayPropertyBinding: TwoWayPropertyBinding<*, *>) {
        twoWayPropertyBindings.add(twoWayPropertyBinding)
    }

    public fun <T, R> addTwoWayPropertyBinding(key: String, observable: Observable<T>, observer: Action1<in T>, converter: TwoWayConverter<T, R> = EmptyTwoWayConverter<T, R>()) {
        twoWayPropertyBindings.add(TwoWayPropertyBinding(key, observable, observer, converter))
    }

    public fun addMultiplePropertyBinding(multiplePropertyBinding: OneWayPropertyBinding<*, *>) {
        multiplePropertyBindings.add(multiplePropertyBinding)
    }

    public fun addCommandBinding(commandBinding: CommandBinding) {
        commandBindings.add(commandBinding)
    }

    public fun addCommandBinding(key: String, trigger: Observable<Unit>, canExecute: Action1<in Boolean> = Action1 {}) {
        commandBindings.add(CommandBinding(key, trigger, canExecute))
    }

    public fun bindTo(bindingContext: BindingContext<*>, viewModel: ViewModel<*>) {
        oneWayPropertyBindings().forEach { propertyBinding -> viewModel.bindProperty(bindingContext, propertyBinding) }
        twoWayPropertyBindings().forEach { propertyBinding -> viewModel.bindProperty(bindingContext, propertyBinding) }
        multiplePropertyBindings().forEach { propertyBinding -> viewModel.bindProperties(bindingContext, propertyBinding) }
        commandBindings().forEach { commandBinding -> viewModel.bindCommand(bindingContext, commandBinding) }
    }
}
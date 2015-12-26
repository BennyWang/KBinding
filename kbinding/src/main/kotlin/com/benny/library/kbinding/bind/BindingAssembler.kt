package com.benny.library.kbinding.bind

import com.benny.library.kbinding.converter.*
import rx.Observable
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription
import java.util.*

/**
 * Created by benny on 11/18/15.
 */

public inline fun <T, R> oneWayPropertyBinding(key: String, observable: Observable<T>, converter: OneWayConverter<R> = EmptyOneWayConverter<R>()) : OneWayPropertyBinding<T, R> {
    return OneWayPropertyBinding(key, observable, converter)
}

public inline fun <T> oneWayPropertyBinding(key: String, observer: Action1<in T>, backConverter: OneWayConverter<T> = EmptyOneWayConverter<T>()) : OneWayPropertyBinding<T, Any> {
    return OneWayPropertyBinding(key, observer, backConverter)
}

public inline fun <T> multiplePropertyBinding(keys: List<String>, observer: Action1<in T>, multipleConverter: MultipleConverter<T>) : MultiplePropertyBinding<T> {
    return MultiplePropertyBinding(keys, observer, multipleConverter)
}

public inline fun <T, R> twoWayPropertyBinding(key: String, observable: Observable<T>, observer: Action1<in T>, converter: TwoWayConverter<T, R> = EmptyTwoWayConverter<T, R>()) : TwoWayPropertyBinding<T, R> {
    return TwoWayPropertyBinding(key, observable, observer, converter)
}

public inline fun <T> commandBinding(key: String, trigger: Observable<T>, canExecute: Action1<in Boolean> = Action1 {}) : CommandBinding<T> {
    return CommandBinding(key, trigger, canExecute)
}

open public class BindingAssembler {
    private val oneWayPropertyBindings = ArrayList<OneWayPropertyBinding<*, *>>()
    private val multiplePropertyBindings = ArrayList<MultiplePropertyBinding<*>>()
    private val twoWayPropertyBindings = ArrayList<TwoWayPropertyBinding<*, *>>()
    private var commandBindings = ArrayList<CommandBinding<*>>()

    fun oneWayPropertyBindings(): List<OneWayPropertyBinding<*, *>> {
        return oneWayPropertyBindings
    }

    fun twoWayPropertyBindings(): List<TwoWayPropertyBinding<*, *>> {
        return twoWayPropertyBindings
    }

    fun multiplePropertyBindings(): List<MultiplePropertyBinding<*>> {
        return multiplePropertyBindings
    }

    fun commandBindings(): List<CommandBinding<*>> {
        return commandBindings
    }

    public fun addBinding(propertyBinding: PropertyBinding): Unit {
        when (propertyBinding) {
            is CommandBinding<*> -> commandBindings.add(propertyBinding)
            is OneWayPropertyBinding<*, *> -> oneWayPropertyBindings.add(propertyBinding)
            is MultiplePropertyBinding<*> -> multiplePropertyBindings.add(propertyBinding)
            is TwoWayPropertyBinding<*, *> -> twoWayPropertyBindings.add(propertyBinding)
            else -> { }
        }
    }

    public fun bindTo(bindingDisposer: BindingDisposer, viewModel: ViewModel): Unit {
        val cs: CompositeSubscription = CompositeSubscription()
        oneWayPropertyBindings().forEach { propertyBinding -> cs.add(propertyBinding.bindTo(viewModel.property<Any>(propertyBinding.key))) }
        twoWayPropertyBindings().forEach { propertyBinding -> cs.add(propertyBinding.bindTo(viewModel.property<Any>(propertyBinding.key))) }
        multiplePropertyBindings().forEach { propertyBinding -> cs.add(propertyBinding.bindTo(viewModel.properties(propertyBinding.keys))) }
        commandBindings().forEach { commandBinding: CommandBinding<*> -> cs.add(commandBinding.bindTo(viewModel.command(commandBinding.key))) }
        bindingDisposer.add { cs.unsubscribe() }
    }

    public fun merge(prefix: String, assembler: BindingAssembler) {
        assembler.oneWayPropertyBindings.forEach { it -> oneWayPropertyBindings.add(it.prefix(prefix)) }
        assembler.multiplePropertyBindings.forEach { it -> multiplePropertyBindings.add(it.prefix(prefix)) }
        assembler.twoWayPropertyBindings.forEach { it -> twoWayPropertyBindings.add(it.prefix(prefix)) }
        assembler.commandBindings.forEach { it -> commandBindings.add(it.prefix(prefix)) }
    }
}
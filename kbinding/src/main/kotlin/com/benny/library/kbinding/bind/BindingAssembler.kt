package com.benny.library.kbinding.bind

import com.benny.library.kbinding.converter.*
import com.benny.library.kbinding.viewmodel.IViewModel
import rx.Observable
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription
import java.util.*

/**
 * Created by benny on 11/18/15.
 */

fun <T, R> oneWayPropertyBinding(key: String, observable: Observable<T>, converter: OneWayConverter<T, R> = EmptyOneWayConverter2<T, R>()) : OneWayPropertyBinding<T, R> {
    return OneWayPropertyBinding(key, observable, converter)
}

@Suppress("UNCHECKED_CAST")
fun <T> oneWayPropertyBinding(keys: Array<out String>, observer: Action1<in T>, oneTime: Boolean, backConverter: OneWayConverter<*, T> = EmptyOneWayConverter1<T>()) : PropertyBinding {
    return if(keys.size == 1) OneWayPropertyBinding(keys[0], observer, oneTime, backConverter as OneWayConverter<Any?, T>) else MultiplePropertyBinding(keys.toList(), observer, oneTime, backConverter as OneWayConverter<Array<Any?>, T>)
}

fun <T, R> twoWayPropertyBinding(key: String, observable: Observable<T>, observer: Action1<in T>, converter: TwoWayConverter<T, R> = EmptyTwoWayConverter<T, R>()) : TwoWayPropertyBinding<T, R> {
    return TwoWayPropertyBinding(key, observable, observer, converter)
}

fun <T> commandBinding(key: String, trigger: Observable<T>, canExecute: Action1<in Boolean> = Action1 {}) : CommandBinding<T> {
    return CommandBinding(key, trigger, canExecute)
}

open class BindingAssembler {
    private val oneWayPropertyBindings = ArrayList<OneWayPropertyBinding<*, *>>()
    private val multiplePropertyBindings = ArrayList<MultiplePropertyBinding<*>>()
    private val twoWayPropertyBindings = ArrayList<TwoWayPropertyBinding<*, *>>()
    private var commandBindings = ArrayList<CommandBinding<*>>()

    fun addBinding(propertyBinding: PropertyBinding): Unit {
        when (propertyBinding) {
            is CommandBinding<*> -> commandBindings.add(propertyBinding)
            is OneWayPropertyBinding<*, *> -> oneWayPropertyBindings.add(propertyBinding)
            is MultiplePropertyBinding<*> -> multiplePropertyBindings.add(propertyBinding)
            is TwoWayPropertyBinding<*, *> -> twoWayPropertyBindings.add(propertyBinding)
            else -> { }
        }
    }

    fun bindTo(bindingDisposer: BindingDisposer, viewModel: IViewModel): Unit {
        CompositeSubscription().apply {
            oneWayPropertyBindings.forEach { propertyBinding -> add(viewModel.bind(propertyBinding)) }
            twoWayPropertyBindings.forEach { propertyBinding -> add(viewModel.bind(propertyBinding)) }
            multiplePropertyBindings.forEach { propertyBinding -> add(viewModel.bind(propertyBinding)) }
            commandBindings.forEach { commandBinding: CommandBinding<*> -> add(viewModel.bind(commandBinding)) }
            bindingDisposer.add { unsubscribe() }
        }
    }

    fun merge(prefix: String, assembler: BindingAssembler) {
        assembler.oneWayPropertyBindings.forEach { it -> oneWayPropertyBindings.add(it.prefix(prefix)) }
        assembler.multiplePropertyBindings.forEach { it -> multiplePropertyBindings.add(it.prefix(prefix)) }
        assembler.twoWayPropertyBindings.forEach { it -> twoWayPropertyBindings.add(it.prefix(prefix)) }
        assembler.commandBindings.forEach { it -> commandBindings.add(it.prefix(prefix)) }
    }

    /*private fun clear() {
        oneWayPropertyBindings.clear()
        twoWayPropertyBindings.clear()
        multiplePropertyBindings.clear()
        commandBindings.clear()
    }*/
}
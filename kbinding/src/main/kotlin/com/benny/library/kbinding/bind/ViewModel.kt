package com.benny.library.kbinding.bind;

import rx.Subscription
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty

/**
 * Created by benny on 11/17/15.
 */
public open class ViewModel() : IViewModel {
    private val delegate: BindingDelegate = BindingDelegate()

    override val properties: MutableMap<String, Property<*>> = delegate.properties
    override val commands: MutableMap<String, Command<*>> = delegate.commands

    override fun <T, R> bind(oneWayPropertyBinding: OneWayPropertyBinding<T, R>): Subscription = delegate.bind(oneWayPropertyBinding)

    override fun <T> bind(multiplePropertyBinding: MultiplePropertyBinding<T>): Subscription = delegate.bind(multiplePropertyBinding)

    override fun <T, R> bind(twoWayPropertyBinding: TwoWayPropertyBinding<T, R>): Subscription = delegate.bind(twoWayPropertyBinding)

    override fun <T> bind(commandBinding: CommandBinding<T>): Subscription = delegate.bind(commandBinding)

    public fun <T> bindProperty(key: String, initialValue: T): ReadWriteProperty<Any?, T> = delegate.bindProperty(key, initialValue)

    public fun bindProperty(key: String, initialValue: IViewModel): ReadOnlyProperty<Any?, IViewModel> = delegate.bindProperty(key, initialValue)

    public fun <T> bindProperty(key: String): ReadWriteProperty<Any?, T?> = delegate.bindProperty(key)

    public fun <T> bindProperty(key: String, dependOf: List<String>, getter: () -> T): ReadOnlyProperty<Any?, T?> = delegate.bindProperty(key, dependOf, getter)

    public fun <T> bindProperty(key: String, dependOf: String, getter: () -> T): ReadOnlyProperty<Any?, T?> = delegate.bindProperty(key, dependOf, getter)

    public fun <T> bindCommand(key: String, initialValue: Command<T>): ReadOnlyProperty<Any?, Command<T>> = delegate.bindCommand(key, initialValue)
}

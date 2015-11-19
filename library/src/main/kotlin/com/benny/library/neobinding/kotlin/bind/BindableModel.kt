package com.benny.library.neobinding.kotlin.bind;

import java.util.*

/**
 * Created by benny on 11/17/15.
 */
abstract public class BindableModel<T> {
    private val properties : MutableMap<String, Property<*>> = HashMap()
    private val commands : MutableMap<String, Command<*>> = HashMap()

    init {
        initCommand()
    }

    public fun <T> property(key: String) : Property<T> {
        val property: Property<T>? = properties.get(key) as Property<T>? ?: throw RuntimeException("invalid key:$key for binding")
        return property as Property<T>;
    }

    public fun <T> command(key: String) : Command<T> {
        val command: Command<T>? = commands.get(key) as Command<T>? ?: throw RuntimeException("invalid key:$key for binding")
        return command as Command<T>
    }

    public fun addProperty(key: String, property: Property<*>) {
        properties.put(key, property)
    }

    public fun addCommand(key: String, command: Command<*>) {
        commands.put(key, command)
    }

    private fun ensurePropertyInitialized(key: String) {
        if (property<Property<*>>(key).hasCompleted) {
            initProperty()
        }
    }

    fun bindProperty(bindingContext: BindingContext<*>, observer: OneWayPropertyBinding<*, *>) {
        ensurePropertyInitialized(observer.key)
        observer.bindTo(bindingContext, property<Any>(observer.key))
    }

    fun bindProperty(bindingContext: BindingContext<*>, observable: TwoWayPropertyBinding<*, *>) {
        ensurePropertyInitialized(observable.key)
        observable.bindTo(bindingContext, property<Any>(observable.key))
    }

    fun bindProperties(bindingContext: BindingContext<*>, observer: OneWayPropertyBinding<*, *>) {
        ensurePropertyInitialized(observer.key)
        val props = ArrayList<Property<*>>()
        observer.keys?.forEach { key -> props.add(property<Any>(key)) }
        observer.bindTo(bindingContext, props)
    }

    fun bindCommand(bindingContext: BindingContext<*>, observable: CommandBinding<*>) {
        observable.bindTo(bindingContext, command<Any>(observable.key))
    }

    public fun notifyPropertyChange(t: T?, position: Int) {
        notifyPropertyChange(t)
    }

    public abstract fun notifyPropertyChange(t: T?)

    abstract protected fun initProperty()
    abstract protected fun initCommand()

}

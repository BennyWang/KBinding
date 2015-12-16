package com.benny.library.neobinding.bind;

import rx.Observable
import java.util.*
import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by benny on 11/17/15.
 */
abstract public class ViewModel<T> {
    val properties : MutableMap<String, Property<*>> = HashMap()
    val commands : MutableMap<String, Command> = HashMap()

    public fun <T> property(key: String) : Property<T> {
        val property: Property<T>? = properties[key] as Property<T>? ?: throw RuntimeException("invalid key:$key for binding")
        return property as Property<T>;
    }

    public fun command(key: String) : Command {
        val command: Command? = commands[key] ?: throw RuntimeException("invalid key:$key for binding")
        return command as Command
    }

    public fun addProperty(key: String, property: Property<*>) {
        properties.put(key, property)
    }

    public fun addCommand(key: String, command: Command) {
        commands.put(key, command)
    }

    fun bindProperty(bindingContext: BindingContext, observer: OneWayPropertyBinding<*, *>) {
        observer.bindTo(bindingContext, property<Any>(observer.key))
    }

    fun bindProperty(bindingContext: BindingContext, observable: TwoWayPropertyBinding<*, *>) {
        observable.bindTo(bindingContext, property<Any>(observable.key))
    }

    fun bindProperties(bindingContext: BindingContext, observer: MultiplePropertyBinding<*>) {
        val props = ArrayList<Property<*>>()
        observer.keys?.forEach { key -> props.add(property<Any>(key)) }
        observer.bindTo(bindingContext, props)
    }

    fun bindCommand(bindingContext: BindingContext, observable: CommandBinding) {
        observable.bindTo(bindingContext, command(observable.key))
    }

    public abstract fun notifyPropertyChange(t: T?)

    public fun <T> Delegates.bindProperty(key: String, initialValue: T): ReadWriteProperty<Any?, T> {
        this@ViewModel.addProperty(key, Property(initialValue))
        //support for nested view model
        if(initialValue is ViewModel<*>) {
            for((k, v) in initialValue.properties) this@ViewModel.addProperty("$key.$k", v)
            for((k, v) in initialValue.commands) this@ViewModel.addCommand("$key.$k", v)
        }

        return object : ReadWriteProperty<Any?, T> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): T = property<T>(property.name).value ?: initialValue
            override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) { property<T>(property.name).value = value }
        }
    }

    public fun Delegates.bindCommand(key: String, initialValue: Command): ReadOnlyProperty<Any?, Command > {
        this@ViewModel.addCommand(key, initialValue)
        return object : ReadOnlyProperty<Any?, Command > {
            override fun getValue(thisRef: Any?, property: KProperty<*>): Command = command(property.name)
        }
    }
}

package com.benny.library.kbinding.bind;

import com.benny.library.kbinding.converter.MultipleConverter
import com.benny.library.kbinding.converter.OneWayConverter
import rx.Observable
import rx.functions.Action1
import java.util.*
import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by benny on 11/17/15.
 */
abstract public class ViewModel() {
    val properties : MutableMap<String, Property<*>> = HashMap()
    val commands : MutableMap<String, Command> = HashMap()

    public fun <T> property(key: String) : Property<T> {
        val property: Property<T>? = properties[key] as Property<T>? ?: throw RuntimeException("invalid key:$key for binding")
        return property as Property<T>;
    }

    public fun properties(keys: List<String>): List<Property<*>> {
        return keys.map { it -> property<Any>(it) }
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

    public fun <T> addDependOf(key: String, keys: List<String>, getter: () -> T) {
        multiplePropertyBinding(keys, Action1 { t -> property<T>(key).observer.onNext(t) }, object : MultipleConverter<T> {
            override fun convert(params: Array<Any>): T = getter()
        }).bindTo(properties(keys))
    }

    public fun <T> addDependOf(key: String, path: String, getter: () -> T) {
        oneWayPropertyBinding(path, Action1 { t -> property<T>(key).observer.onNext(t) }, object : OneWayConverter<T> {
            override fun convert(source: Any?): T = getter()
        }).bindTo(property(path))
    }

    protected fun <T> Delegates.bindProperty(key: String, initialValue: T): ReadWriteProperty<Any?, T> {
        this@ViewModel.addProperty(key, Property(initialValue))
        //support for nested view model
        if(initialValue is ViewModel) {
            for((k, v) in initialValue.properties) this@ViewModel.addProperty("$key.$k", v)
            for((k, v) in initialValue.commands) this@ViewModel.addCommand("$key.$k", v)
        }

        return object : ReadWriteProperty<Any?, T> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): T = property<T>(property.name).value ?: initialValue
            override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) { property<T>(property.name).value = value }
        }
    }

    protected fun <T> Delegates.bindProperty(key: String): ReadWriteProperty<Any?, T?> {
        this@ViewModel.addProperty(key, Property<T>())

        return object : ReadWriteProperty<Any?, T?> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): T? = property<T>(property.name).value
            override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) { property<T>(property.name).value = value }
        }
    }

    protected fun <T> Delegates.bindProperty(key: String, keys: List<String>, getter: () -> T): ReadOnlyProperty<Any?, T?> {
        // dose not need support nested view model
        this@ViewModel.addProperty(key, Property<T>())
        addDependOf(key, keys, getter)

        return object : ReadOnlyProperty<Any?, T?> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): T? = property<T>(property.name).value
        }
    }

    protected fun <T> Delegates.bindProperty(key: String, path: String, getter: () -> T): ReadOnlyProperty<Any?, T?> {
        // dose not need support nested view model
        this@ViewModel.addProperty(key, Property<T>())
        addDependOf(key, path, getter)
        return object : ReadOnlyProperty<Any?, T?> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): T? = property<T>(property.name).value
        }
    }

    protected fun Delegates.bindCommand(key: String, initialValue: Command): ReadOnlyProperty<Any?, Command> {
        this@ViewModel.addCommand(key, initialValue)
        return object : ReadOnlyProperty<Any?, Command> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): Command = command(property.name)
        }
    }
}

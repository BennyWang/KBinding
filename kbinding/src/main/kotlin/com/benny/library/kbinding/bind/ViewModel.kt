package com.benny.library.kbinding.bind;

import rx.Subscription
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

import com.benny.library.kbinding.converter.MultipleConverter
import java.security.InvalidParameterException

/**
 * Created by benny on 11/17/15.
 */

@Suppress("UNCHECKED_CAST")
open class ViewModel() : IViewModel {
    class MockSubscription : Subscription {
        override fun isUnsubscribed(): Boolean = true
        override fun unsubscribe() {}
    }

    val dependsOn: MutableMap<String, PropertyBinding> = HashMap()

    override val properties : MutableMap<String, Property<*>> = HashMap()
    override val commands : MutableMap<String, Command<*>> = HashMap()

    override fun <T, R> bind(oneWayPropertyBinding: OneWayPropertyBinding<T, R>) : Subscription {
        val cs: CompositeSubscription = CompositeSubscription()
        cs.add(bindDependsOn(oneWayPropertyBinding.key))
        cs.add(oneWayPropertyBinding.bindTo(property(oneWayPropertyBinding.key)))
        return cs
    }

    override fun <T> bind(multiplePropertyBinding: MultiplePropertyBinding<T>) : Subscription {
        val cs: CompositeSubscription = CompositeSubscription()
        multiplePropertyBinding.keys.forEach { cs.add(bindDependsOn(it)) }
        cs.add(multiplePropertyBinding.bindTo(properties(multiplePropertyBinding.keys)))
        return cs
    }

    override fun <T, R> bind(twoWayPropertyBinding: TwoWayPropertyBinding<T, R>) : Subscription {
        return twoWayPropertyBinding.bindTo(property(twoWayPropertyBinding.key))
    }

    override fun <T> bind(commandBinding: CommandBinding<T>) : Subscription {
        return commandBinding.bindTo(command(commandBinding.key))
    }

    private fun bindDependsOn(key: String) : Subscription {
        val dependsOn = dependsOn.remove(key) ?: return MockSubscription()
        return when(dependsOn) {
            is OneWayPropertyBinding<*, *> -> (dependsOn as OneWayPropertyBinding<Any, Any>).bindTo(property<Any>(dependsOn.key))
            is MultiplePropertyBinding<*> -> dependsOn.bindTo(properties(dependsOn.keys))
            else -> MockSubscription()
        }
    }

    private fun <T> property(key: String) : Property<T> {
        val property: Property<T> = properties[key] as? Property<T> ?: throw RuntimeException("invalid key:$key for binding")
        return property;
    }

    private fun properties(keys: List<String>): List<Property<*>> {
        return keys.map { it -> property<Any>(it) }
    }

    private fun <T> command(key: String) : Command<T> {
        val command: Command<T> = commands[key] as? Command<T> ?: throw RuntimeException("invalid key:$key for binding")
        return command
    }

    private fun addProperty(key: String, property: Property<*>) {
        properties.put(key, property)
    }

    private fun <T> addCommand(key: String, command: Command<T>) {
        commands.put(key, command)
    }

    private fun <T> addDependsOn(key: String, dependsOn: Array<out String>, getter: () -> T) {
        this.dependsOn.put(key, oneWayPropertyBinding(dependsOn, Action1<T> { t -> property<T>(key).value = t }, false, object : MultipleConverter<T> {
            override fun convert(params: Array<Any>): T = getter()
            override fun convert(source: Any): T = getter()
        }))
    }

    fun <T> bindProperty(key: String): ReadWriteProperty<Any, T?> {
        addProperty(key, Property<T>())

        return object : ReadWriteProperty<Any, T?> {
            override fun getValue(thisRef: Any, property: KProperty<*>): T? = property<T>(property.name).value
            override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) { property<T>(property.name).value = value }
        }
    }

    fun <T> bindProperty(vararg keys: String, getter: () -> T): ReadWriteProperty<Any, T> {
        if(keys.size == 0) throw InvalidParameterException("at least one key")

        val key = keys[0]

        if(keys.size == 1) {
            val initialValue = getter();
            addProperty(key, Property(initialValue))

            //support for nested view model
            if(initialValue != null && initialValue is IViewModel) {
                for((k, v) in initialValue.properties) addProperty("$key.$k", v)
                for((k, v) in initialValue.commands) addCommand("$key.$k", v)
            }
        }
        else {
            addProperty(key, Property<T>())
            addDependsOn(key, keys.sliceArray(1..(keys.size - 1)), getter = getter)
        }

        return object : ReadWriteProperty<Any, T> {
            override fun getValue(thisRef: Any, property: KProperty<*>): T = property<T>(property.name).value ?: getter()
            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
                if(keys.size > 1) throw InvalidParameterException("depends property can not be set")
                property<T>(property.name).value = value
            }
        }
    }

    fun <T> bindCommand(key: String, cmdAction: (T, (Boolean) -> Unit) -> Unit): ReadOnlyProperty<Any, Command<T>> {
        addCommand(key, Command<T> { t, action -> cmdAction(t, { action.call(it) }) })
        return object : ReadOnlyProperty<Any, Command<T>> {
            override fun getValue(thisRef: Any, property: KProperty<*>): Command<T> = command(property.name)
        }
    }
}

package com.benny.library.kbinding.bind;

import com.benny.library.kbinding.converter.MultipleConverter
import com.benny.library.kbinding.converter.OneWayConverter
import rx.Subscription
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by benny on 11/17/15.
 */
public open class ViewModel() : IViewModel {
    class MockSubscription : Subscription {
        override fun isUnsubscribed(): Boolean = true
        override fun unsubscribe() {}
    }

    val dependsOf : MutableMap<String, PropertyBinding> = HashMap()

    override val properties : MutableMap<String, Property<*>> = HashMap()
    override val commands : MutableMap<String, Command<*>> = HashMap()

    override fun <T, R> bind(oneWayPropertyBinding: OneWayPropertyBinding<T, R>) : Subscription {
        val cs: CompositeSubscription = CompositeSubscription()
        cs.add(bindDependOf(oneWayPropertyBinding.key))
        cs.add(oneWayPropertyBinding.bindTo(property(oneWayPropertyBinding.key)))
        return cs
    }

    override fun <T> bind(multiplePropertyBinding: MultiplePropertyBinding<T>) : Subscription {
        val cs: CompositeSubscription = CompositeSubscription()
        multiplePropertyBinding.keys.forEach { cs.add(bindDependOf(it)) }
        cs.add(multiplePropertyBinding.bindTo(properties(multiplePropertyBinding.keys)))
        return cs
    }

    override fun <T, R> bind(twoWayPropertyBinding: TwoWayPropertyBinding<T, R>) : Subscription {
        return twoWayPropertyBinding.bindTo(property(twoWayPropertyBinding.key))
    }

    override fun <T> bind(commandBinding: CommandBinding<T>) : Subscription {
        return commandBinding.bindTo(command(commandBinding.key))
    }

    private fun bindDependOf(key: String) : Subscription {
        val dependOf = dependsOf.remove(key) ?: return MockSubscription()
        return when(dependOf) {
            is OneWayPropertyBinding<*, *> -> dependOf.bindTo(property<Any>(dependOf.key))
            is MultiplePropertyBinding<*> -> dependOf.bindTo(properties(dependOf.keys))
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

    private fun <T> addDependOf(key: String, dependOf: List<String>, getter: () -> T) {
        dependsOf.put(key, multiplePropertyBinding(dependOf, Action1 { t -> property<T>(key).value = t }, false, object : MultipleConverter<T> {
            override fun convert(params: Array<Any>): T = getter()
        }))
    }

    private fun <T> addDependOf(key: String, dependOf: String, getter: () -> T) {
        dependsOf.put(key, oneWayPropertyBinding(dependOf, Action1 { t -> property<T>(key).value = t }, false, object : OneWayConverter<T> {
            override fun convert(source: Any?): T = getter()
        }))
    }

    public fun <T> bindProperty(key: String, initialValue: T): ReadWriteProperty<Any?, T> {
        addProperty(key, Property(initialValue))
        //support for nested view model
        if(initialValue is IViewModel) {
            for((k, v) in initialValue.properties) addProperty("$key.$k", v)
            for((k, v) in initialValue.commands) addCommand("$key.$k", v)
        }

        return object : ReadWriteProperty<Any?, T> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): T = property<T>(property.name).value ?: initialValue
            override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) { property<T>(property.name).value = value }
        }
    }

    public fun bindProperty(key: String, initialValue: IViewModel): ReadOnlyProperty<Any?, IViewModel> {
        //support for nested view model
        if(initialValue is IViewModel) {
            for((k, v) in initialValue.properties) addProperty("$key.$k", v)
            for((k, v) in initialValue.commands) addCommand("$key.$k", v)
        }

        return object : ReadOnlyProperty<Any?, IViewModel> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): IViewModel = initialValue
        }
    }

    public fun <T> bindProperty(key: String): ReadWriteProperty<Any?, T?> {
        addProperty(key, Property<T>())

        return object : ReadWriteProperty<Any?, T?> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): T? = property<T>(property.name).value
            override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) { property<T>(property.name).value = value }
        }
    }

    public fun <T> bindProperty(key: String, dependOf: List<String>, getter: () -> T): ReadOnlyProperty<Any?, T> {
        // dose not need support nested view model
        addProperty(key, Property<T>())
        addDependOf(key, dependOf, getter)

        return object : ReadOnlyProperty<Any?, T> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): T = property<T>(property.name).value ?: getter()
        }
    }

    public fun <T> bindProperty(key: String, dependOf: String, getter: () -> T): ReadOnlyProperty<Any?, T> {
        // dose not need support nested view model
        addProperty(key, Property<T>())
        addDependOf(key, dependOf, getter)
        return object : ReadOnlyProperty<Any?, T> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): T = property<T>(property.name).value ?: getter()
        }
    }

    public fun <T> bindCommand(key: String, initialValue: Command<T>): ReadOnlyProperty<Any?, Command<T>> {
        addCommand(key, initialValue)
        return object : ReadOnlyProperty<Any?, Command<T>> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): Command<T> = command(property.name)
        }
    }
}

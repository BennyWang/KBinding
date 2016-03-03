package com.benny.library.kbinding.bind;

import android.util.Log
import com.benny.library.kbinding.converter.OneWayConverter
import com.benny.library.kbinding.internal.BindingInitializer
import rx.Subscription
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription
import java.security.InvalidParameterException
import java.util.*
import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by benny on 11/17/15.
 */

class MockSubscription : Subscription {
    override fun isUnsubscribed(): Boolean = true
    override fun unsubscribe() {}
}

@Suppress("UNCHECKED_CAST")
open class ViewModel() : IViewModel {
    override val properties : MutableMap<String, Property<*>> = HashMap()
    override val commands : MutableMap<String, Command<*>> = HashMap()

    val dependsOn: MutableMap<String, PropertyBinding> = HashMap()

    init {
        BindingInitializer.init(this)
    }

    private fun <T> addDependsOn(key: String, dependsOn: Array<out String>, getter: () -> T) = this.dependsOn.put(key,
            oneWayPropertyBinding(dependsOn, Action1<T> { t -> property<T>(key).value = t }, false, OneWayConverter<Any?, T> { getter() }))

    override fun <T, R> bind(oneWayPropertyBinding: OneWayPropertyBinding<T, R>) : Subscription {
        return CompositeSubscription().apply {
            add(bindDependsOn(oneWayPropertyBinding.key))
            add(oneWayPropertyBinding.bindTo(property(oneWayPropertyBinding.key)))
        }
    }

    override fun <T> bind(multiplePropertyBinding: MultiplePropertyBinding<T>) : Subscription {
        return CompositeSubscription().apply {
            multiplePropertyBinding.keys.forEach { add(bindDependsOn(it)) }
            add(multiplePropertyBinding.bindTo(properties(multiplePropertyBinding.keys)))
        }
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

    fun <T> bindProperty(key: String): ReadWriteProperty<Any, T?> {
        addProperty(key, Property<T>())

        return object : ReadWriteProperty<Any, T?> {
            override fun getValue(thisRef: Any, property: KProperty<*>): T? = property<T>(property.name).value
            override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) { property<T>(property.name).value = value }
        }
    }

    fun <T> bindProperty(vararg keys: String, getter: () -> T): ReadWriteProperty<Any, T> {
        if(keys.size == 0) throw InvalidParameterException("at least one key")

        return if(keys.size == 1) {
            bindPropertyInner(keys[0], getter)
        }
        else {
            bindPropertyInner(keys[0], keys.sliceArray(1..(keys.size - 1)), getter = getter)
        }
    }

    fun <T> bindCommand(key: String, cmdAction: (T, (Boolean) -> Unit) -> Unit): ReadOnlyProperty<Any, Command<T>> {
        addCommand(key, Command<T> { t, action -> cmdAction(t, { action.call(it) }) })
        return object : ReadOnlyProperty<Any, Command<T>> {
            override fun getValue(thisRef: Any, property: KProperty<*>): Command<T> = command(property.name)
        }
    }

    private fun <T> bindPropertyInner(key: String, getter: () -> T) : ReadWriteProperty<Any, T> {
        val initialValue = getter();
        addProperty(key, Property(initialValue))

        //support for nested view model
        if(initialValue != null && initialValue is IViewModel) {
            for((k, v) in initialValue.properties) addProperty("$key.$k", v)
            for((k, v) in initialValue.commands) addCommand("$key.$k", v)
        }

        return object : ReadWriteProperty<Any, T> {
            override fun getValue(thisRef: Any, property: KProperty<*>): T = property<T>(property.name).value as T
            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
                property<T>(property.name).value = value
            }
        }
    }

    private fun <T> bindPropertyInner(key: String, dependsOn: Array<out String>, getter: () -> T) : ReadWriteProperty<Any, T> {
        addProperty(key, Property<T>())
        addDependsOn(key, dependsOn, getter = getter)

        return object : ReadWriteProperty<Any, T> {
            override fun getValue(thisRef: Any, property: KProperty<*>): T = getter()
            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = throw InvalidParameterException("depends property can not be set")
        }
    }

    fun <T> bindPropertyV2(key: String, initialValue: T?) {
        addProperty(key, Property(initialValue))
    }

    fun <T> bindDependsOnV2(key: String, dependsOn: Array<String>, getter: () -> T) {
        addDependsOn(key, dependsOn, getter = getter)
    }

    fun <T> Delegates.property(initialValue: T): ReadWriteProperty<Any, T> {
        return object : ReadWriteProperty<Any, T> {
            var isSet = false
            override fun getValue(thisRef: Any, property: KProperty<*>): T = if(isSet) propertyOrNull<T>(property.name)?.value as T else initialValue
            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) { isSet = true; property<T>(property.name).value = value }
        }
    }

    fun <T> Delegates.property(getter: () -> T): ReadWriteProperty<Any, T> {
        return object : ReadWriteProperty<Any, T> {
            override fun getValue(thisRef: Any, property: KProperty<*>): T = getter()
            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) { property<T>(property.name).value = value }
        }
    }
}

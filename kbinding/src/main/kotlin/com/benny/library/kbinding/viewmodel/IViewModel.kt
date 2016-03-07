package com.benny.library.kbinding.viewmodel

import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.converter.OneWayConverter
import rx.Subscription
import rx.functions.Action1

/**
 * Created by benny on 12/29/15.
 */
interface IViewModel {
    val properties : MutableMap<String, Property<*>>
    val commands : MutableMap<String, Command<*>>

    var hasInitialized: Boolean

    fun <T, R> bind(oneWayPropertyBinding: OneWayPropertyBinding<T, R>) : Subscription
    fun <T> bind(multiplePropertyBinding: MultiplePropertyBinding<T>) : Subscription
    fun <T, R> bind(twoWayPropertyBinding: TwoWayPropertyBinding<T, R>) : Subscription
    fun <T> bind(commandBinding: CommandBinding<T>) : Subscription

    @Suppress("UNCHECKED_CAST")
    fun <T> property(key: String) : Property<T> = properties[key] as? Property<T> ?: throw RuntimeException("invalid key:$key for binding")

    @Suppress("UNCHECKED_CAST")
    fun <T> propertyOrNull(key: String) : Property<T>? = properties[key] as? Property<T>

    fun properties(keys: List<String>): List<Property<*>> = keys.map { it -> property<Any>(it) }

    @Suppress("UNCHECKED_CAST")
    fun <T> command(key: String) : Command<T> = commands[key] as? Command<T> ?: throw RuntimeException("invalid key:$key for binding")

    fun addProperty(key: String, property: Property<*>) = properties.put(key, property)

    fun <T> addCommand(key: String, command: Command<T>) = commands.put(key, command)

}
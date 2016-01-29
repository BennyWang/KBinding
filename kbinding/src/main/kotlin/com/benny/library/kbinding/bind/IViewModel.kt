package com.benny.library.kbinding.bind

import rx.Subscription
import java.util.*

/**
 * Created by benny on 12/29/15.
 */
interface IViewModel {
    val properties : MutableMap<String, Property<*>>
    val commands : MutableMap<String, Command<*>>

    fun <T, R> bind(oneWayPropertyBinding: OneWayPropertyBinding<T, R>) : Subscription
    fun <T> bind(multiplePropertyBinding: MultiplePropertyBinding<T>) : Subscription
    fun <T, R> bind(twoWayPropertyBinding: TwoWayPropertyBinding<T, R>) : Subscription
    fun <T> bind(commandBinding: CommandBinding<T>) : Subscription
}
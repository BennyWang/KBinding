package com.benny.library.kbinding.bind

import com.benny.library.kbinding.converter.OneWayConverter
import com.benny.library.kbinding.viewmodel.Property
import rx.Observable
import rx.Subscription
import rx.functions.Action1

/**
 * Created by benny on 11/17/15.
 */

class MultiplePropertyBinding<T>(keys: List<String>, val observer: Action1<in T>, val oneTime: Boolean, val multipleConverter: OneWayConverter<Array<Any?>, T>) : PropertyBinding() {
    var keys: List<String> = keys
    private set

    fun bindTo(properties: List<Property<*>>): Subscription {
        val ob = Observable.combineLatest(properties.map { property -> property.observable }, { multipleConverter.convert(it) })
                .doOnSubscribe { LogBind(keys, "OneWay") }
                .doOnUnsubscribe { LogUnbind(keys, "OneWay") }
        return if(!oneTime) ob.subscribe(observer) else ob.take(1).subscribe(observer)
    }

    fun prefix(prefix: String) : MultiplePropertyBinding<T> {
        if(!prefix.isEmpty()) keys = keys.map { it -> "$prefix.$it" }
        return this
    }
}
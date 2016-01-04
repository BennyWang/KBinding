package com.benny.library.kbinding.bind

import com.benny.library.kbinding.converter.EmptyOneWayConverter
import com.benny.library.kbinding.converter.MultipleConverter
import com.benny.library.kbinding.converter.OneWayConverter
import rx.Observable
import rx.Subscription
import rx.functions.Action1

/**
 * Created by benny on 11/17/15.
 */

public class MultiplePropertyBinding<T>(keys: List<String>, val observer: Action1<in T>, val oneTime: Boolean, val multipleConverter: MultipleConverter<T>) : PropertyBinding() {
    public var keys: List<String> = keys
    private set

    public fun bindTo(properties: List<Property<*>>): Subscription {
        val ob = Observable.combineLatest(properties.map { property -> property.observable }, { multipleConverter.convert(it) })
                .doOnSubscribe { LogBind(keys, "OneWay") }
                .doOnUnsubscribe { LogUnbind(keys, "OneWay") }
        return if(!oneTime) ob.subscribe(observer) else ob.take(1).subscribe(observer)
    }

    public fun prefix(prefix: String) : MultiplePropertyBinding<T> {
        if(!prefix.isEmpty()) keys = keys.map { it -> "$prefix.$it" }
        return this
    }
}
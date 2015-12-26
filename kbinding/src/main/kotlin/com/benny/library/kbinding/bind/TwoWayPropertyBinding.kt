package com.benny.library.kbinding.bind

import android.util.Log
import com.benny.library.kbinding.converter.EmptyTwoWayConverter
import com.benny.library.kbinding.converter.TwoWayConverter
import rx.Observable
import rx.Subscription
import rx.functions.Action1
import rx.functions.Func1
import rx.subscriptions.CompositeSubscription

/**
 * Created by benny on 11/17/15.
 */

public class TwoWayPropertyBinding<T, R>(key: String, val observable: Observable<T>, val observer: Action1<in T>, converter: TwoWayConverter<T, R> = EmptyTwoWayConverter()) : PropertyBinding() {
    public var key: String = key
        private set

    val converter: TwoWayConverter<T, R> = converter

    public fun prefix(prefix: String): TwoWayPropertyBinding<T, R> {
        if(!prefix.isEmpty()) key = "$prefix.$key"
        return this
    }

    public fun bindTo(property: Property<R>): Subscription {
        val cs: CompositeSubscription = CompositeSubscription()
        val breaker = CircleBreaker<T>()
        cs.add(observable.filter(breaker).map { converter.convert(it as Any) }.subscribe(property.observer))
        cs.add(property.observable.map{ converter.convertBack(it as Any) }.filter(breaker).subscribe(observer))
        return cs
    }

    private class CircleBreaker<T> : Func1<T, Boolean> {
        private var last: T? = null
        override fun call(o: T): Boolean? {
            if (last != null && o.toString() == last!!.toString()) return false
            last = o
            return true
        }
    }
}
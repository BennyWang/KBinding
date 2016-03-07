package com.benny.library.kbinding.bind

import com.benny.library.kbinding.converter.EmptyOneWayConverter2
import com.benny.library.kbinding.converter.OneWayConverter
import com.benny.library.kbinding.viewmodel.Property
import rx.Observable
import rx.Subscription
import rx.functions.Action1

/**
 * Created by benny on 11/17/15.
 */

class OneWayPropertyBinding<T, R> private constructor(key: String, val oneTime: Boolean) : PropertyBinding() {
    var key:String = key
        private set

    var converter: OneWayConverter<T, R>? = null
    var backConverter: OneWayConverter<R, T>? = null

    var observable: Observable<T>? = null
    var observer: Action1<in T>? = null

    constructor(key: String, observable: Observable<T>, converter: OneWayConverter<T, R> = EmptyOneWayConverter2<T, R>()) : this(key, false) {
        this.observable = observable
        this.converter = converter
    }

    constructor(key: String, observer: Action1<in T>, oneTime: Boolean = false, backConverter: OneWayConverter<R, T> = EmptyOneWayConverter2<R, T>()) : this(key, oneTime) {
        this.observer = observer
        this.backConverter = backConverter
    }

    fun prefix(prefix: String) : OneWayPropertyBinding<T, R> {
        if(!prefix.isEmpty()) key = "$prefix.$key"
        return this
    }

    fun bindTo(property: Property<R>): Subscription {
        if (observable != null) {
            return observable!!.map { converter!!.convert(it) }
                    .doOnSubscribe { LogBind(key, "OneWay") }
                    .doOnUnsubscribe { LogUnbind(key, "OneWay") }
                    .subscribe(property.observer)
        }

        val ob = property.observable.map { backConverter!!.convert(it) }
                .doOnSubscribe { LogBind(key, "OneWay") }
                .doOnUnsubscribe { LogUnbind(key, "OneWay") }
                .doOnNext { LogOnNext(key, it) }
                .doOnError { LogError(it) }

        return if(!oneTime) ob.subscribe(observer) else ob.take(1).subscribe(observer)
    }
}
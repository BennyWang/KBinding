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

public class OneWayPropertyBinding<T, R> private constructor(key: String, val oneTime: Boolean) : PropertyBinding() {
    public var key:String = key
        private set

    var converter: OneWayConverter<R>? = null
    var backConverter: OneWayConverter<T>? = null

    var observable: Observable<T>? = null
    var observer: Action1<in T>? = null

    constructor(key: String, observable: Observable<T>, converter: OneWayConverter<R> = EmptyOneWayConverter<R>()) : this(key, false) {
        this.observable = observable
        this.converter = converter
    }

    constructor(key: String, observer: Action1<in T>, oneTime: Boolean = false, backConverter: OneWayConverter<T> = EmptyOneWayConverter<T>()) : this(key, oneTime) {
        this.observer = observer
        this.backConverter = backConverter
    }

    public fun prefix(prefix: String) : OneWayPropertyBinding<T, R> {
        if(!prefix.isEmpty()) key = "$prefix.$key"
        return this
    }

    public fun bindTo(property: Property<R>): Subscription {
        if (observable != null) {
            return observable!!.map { (converter!!).convert(it as Any) }
                    .doOnSubscribe { LogBind(key, "OneWay") }
                    .doOnUnsubscribe { LogUnbind(key, "OneWay") }
                    .subscribe(property.observer)
        }

        val ob = property.observable.map { backConverter!!.convert(it as Any) }
                .doOnSubscribe { LogBind(key, "OneWay") }
                .doOnUnsubscribe { LogUnbind(key, "OneWay") }
                .doOnNext { LogOnNext(key, it) }

        return if(!oneTime) ob.subscribe(observer) else ob.take(1).subscribe(observer)
    }
}
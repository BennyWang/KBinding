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

public class OneWayPropertyBinding<T, R> private constructor(key: String) : PropertyBinding() {
    public var key:String = key
        private set

    var converter: OneWayConverter<R>? = null
    var backConverter: OneWayConverter<T>? = null

    var observable: Observable<T>? = null
    var observer: Action1<in T>? = null

    constructor(key: String, observable: Observable<T>, converter: OneWayConverter<R> = EmptyOneWayConverter<R>()) : this(key) {
        this.observable = observable
        this.converter = converter
    }

    constructor(key: String, observer: Action1<in T>, backConverter: OneWayConverter<T> = EmptyOneWayConverter<T>()) : this(key) {
        this.observer = observer
        this.backConverter = backConverter
    }

    public fun prefix(prefix: String) : OneWayPropertyBinding<T, R> {
        if(!prefix.isEmpty()) key = "$prefix.$key"
        return this
    }

    public fun bindTo(property: Property<R>): Subscription {
        if (observable != null) {
            return observable!!.map { (converter as OneWayConverter<R>).convert(it as Any) }.subscribe(property.observer)
        }

        return property.observable.map { backConverter!!.convert(it as Any) }.subscribe(observer)
    }
}
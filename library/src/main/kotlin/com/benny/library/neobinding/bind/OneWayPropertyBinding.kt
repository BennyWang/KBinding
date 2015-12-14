package com.benny.library.neobinding.bind

import com.benny.library.neobinding.converter.EmptyOneWayConverter
import com.benny.library.neobinding.converter.MultipleConverter
import com.benny.library.neobinding.converter.OneWayConverter
import rx.Observable
import rx.functions.Action1

/**
 * Created by benny on 11/17/15.
 */

public class OneWayPropertyBinding<T, R> private constructor(public val key: String) {
    public var keys: List<String>? = null
        private set

    var converter: OneWayConverter<R>? = null
    var backConverter: OneWayConverter<T>? = null
    var multipleConverter: MultipleConverter<T>? = null

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

    constructor(keys: List<String>, observer: Action1<in T>, multipleConverter: MultipleConverter<T> ) : this(keys.first()) {
        this.keys = keys
        this.observer = observer
        this.multipleConverter = multipleConverter
    }

    public fun bindTo(bindingContext: BindingContext<*>, property: Property<R>) {
        if (observable != null) {
            observable!!.compose(bindingContext.applyLifecycle<T>())
                    .map { (converter as OneWayConverter<R>).convert(it as Any) }
                    .subscribe(property.observer)
        } else {
            property.observable
                    .compose(bindingContext.applyLifecycle<R>())
                    .map { backConverter!!.convert(it as Any) }
                    .subscribe(observer)
        }
    }

    public fun bindTo(bindingContext: BindingContext<*>, properties: List<Property<R>>) {
        Observable.combineLatest(properties.map { property -> property.observable }, { multipleConverter!!.convert(it) })
                .compose(bindingContext.applyLifecycle<T>())
                .subscribe(observer)
    }
}
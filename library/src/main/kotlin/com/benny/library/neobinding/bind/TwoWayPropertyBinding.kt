package com.benny.library.neobinding.bind

import android.util.Log
import com.benny.library.neobinding.converter.EmptyTwoWayConverter
import com.benny.library.neobinding.converter.TwoWayConverter
import rx.Observable
import rx.functions.Action1
import rx.functions.Func1

/**
 * Created by benny on 11/17/15.
 */

public class TwoWayPropertyBinding<T, R>(public val key: String, val observable: Observable<T>, val observer: Action1<in T>, converter: TwoWayConverter<T, R>? = EmptyTwoWayConverter()) : PropertyBinding() {
    val converter: TwoWayConverter<T, R> = converter ?: EmptyTwoWayConverter()

    public fun bindTo(bindingContext: BindingContext<*>, property: Property<R>) {
        val breaker = CircleBreaker<T>()
        observable.filter(breaker)
                .map { converter.convert(it as Any) }.compose(bindingContext.applyLifecycle<R>())
                .subscribe(property.observer)
        property.observable.map{ converter.convertBack(it as Any) }
                .filter(breaker)
                .compose(bindingContext.applyLifecycle<T>())
                .subscribe(observer)
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
package com.benny.library.neobinding.bind

import com.benny.library.neobinding.converter.EmptyOneWayConverter
import com.benny.library.neobinding.converter.MultipleConverter
import com.benny.library.neobinding.converter.OneWayConverter
import rx.Observable
import rx.functions.Action1

/**
 * Created by benny on 11/17/15.
 */

public class MultiplePropertyBinding<T>(val keys: List<String>, val observer: Action1<in T>, val multipleConverter: MultipleConverter<T>) : PropertyBinding() {
    public fun bindTo(bindingContext: BindingContext, properties: List<Property<*>>) {
        Observable.combineLatest(properties.map { property -> property.observable }, { multipleConverter.convert(it) })
                .compose(bindingContext.applyLifecycle<T>())
                .subscribe(observer)
    }
}
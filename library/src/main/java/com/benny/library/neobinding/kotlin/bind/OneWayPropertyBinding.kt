package com.benny.library.neobinding.kotlin.bind

import android.util.Log
import com.benny.library.neobinding.bind.ViewModelBinder
import com.benny.library.neobinding.kotlin.converter.EmptyOneWayConverter
import com.benny.library.neobinding.kotlin.converter.MultipleConverter
import com.benny.library.neobinding.kotlin.converter.OneWayConverter
import rx.Observable
import rx.functions.Action1

/**
 * Created by benny on 11/17/15.
 */

public class OneWayPropertyBinding<T, R> private constructor(public val key: String, val bindingContext: BindingContext<*>) {
    public var keys: List<String>? = null
        private set

    var converter: OneWayConverter<R>? = null
    var backConverter: OneWayConverter<T>? = null
    var multipleConverter: MultipleConverter<T>? = null

    var observable: Observable<T>? = null
    var observer: Action1<T>? = null

    constructor(key: String, bindingContext: BindingContext<*>, observable: Observable<T>, converter: OneWayConverter<R> = EmptyOneWayConverter<R>()) : this(key, bindingContext) {
        this.observable = observable
        this.converter = converter
    }

    constructor(key: String, bindingContext: BindingContext<*>, observer: Action1<T>, backConverter: OneWayConverter<T> = EmptyOneWayConverter<T>()) : this(key, bindingContext) {
        this.observer = observer
        this.backConverter = backConverter
    }

    constructor(keys: List<String>, bindingContext: BindingContext<*>, observer: Action1<T>, multipleConverter: MultipleConverter<T> ) : this(keys.first(), bindingContext) {
        this.keys = keys
        this.observer = observer
        this.multipleConverter = multipleConverter
    }

    public fun bindTo(bindingContext: BindingContext<*>, property: Property<R>) {
        if (observable != null) {
            (observable as Observable<T>)
                    .compose(bindingContext.applyLifecycle<T>())
                    .map { (converter as OneWayConverter<R>).convert(it as Any) }
                    .doOnSubscribe { ViewModelBinder.LogBinding(property.value.toString() + " view->model") }
                    .doOnTerminate { ViewModelBinder.LogUnbinding(property.value.toString() + " view->model") }
                    .subscribe(property.observer)
        } else {
            (property.observable)
                    .compose(bindingContext.applyLifecycle<R>())
                    .map({ (backConverter as OneWayConverter<T>).convert(it as Any) })
                    .doOnSubscribe({ ViewModelBinder.LogBinding(property.value.toString() + " model->view") })
                    .doOnTerminate({ ViewModelBinder.LogUnbinding(property.value.toString() + " model->view") })
                    .subscribe(observer)
        }
    }

    public fun bindTo(bindingContext: BindingContext<*>, properties: List<Property<*>>) {
        Observable.combineLatest(properties.map { property -> property.observable }, { (multipleConverter as MultipleConverter<T>).convert(it) })
                .compose(bindingContext.applyLifecycle<T>())
                .doOnSubscribe { ViewModelBinder.LogBinding(keys?.joinToString(",") + " multiple") }
                .doOnTerminate { ViewModelBinder.LogUnbinding(keys?.joinToString(",") + " multiple") }
                .doOnNext { o -> Log.d("Binding", "multiple onNext:" + o) }
                .subscribe(observer)
    }
}
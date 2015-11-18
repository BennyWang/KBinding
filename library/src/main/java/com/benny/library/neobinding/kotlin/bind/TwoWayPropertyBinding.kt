package com.benny.library.neobinding.kotlin.bind

import android.util.Log
import com.benny.library.neobinding.bind.ViewModelBinder
import com.benny.library.neobinding.kotlin.converter.EmptyTwoWayConverter
import com.benny.library.neobinding.kotlin.converter.TwoWayConverter
import rx.Observable
import rx.functions.Action1
import rx.functions.Func1

/**
 * Created by benny on 11/17/15.
 */

public class TwoWayPropertyBinding<T, R>(public val key: String, val observable: Observable<T>, val observer: Action1<in T>, val converter: TwoWayConverter<T, R> = EmptyTwoWayConverter()) {

    public fun bindTo(bindingContext: BindingContext<*>, property: Property<R>) {
        val breaker = CircleBreaker<T>()
        observable
                .doOnNext { o -> Log.d("TwoWayPropertyBinding", "view->model:" + o.toString()) }
                .filter(breaker)
                .map { converter.convert(it as Any) }.compose(bindingContext.applyLifecycle<R>())
                .doOnSubscribe { ViewModelBinder.LogBinding(property.value.toString() + " view->model") }
                .doOnTerminate { ViewModelBinder.LogUnbinding(property.value.toString() + " view->model") }
                .doOnError { e -> Log.d("Binding", "view->model error:" + e) }
                .subscribe(property.observer)
        property.observable.map{ converter.convertBack(it as Any) }
                .doOnNext({ o -> Log.d("TwoWayPropertyBinding", "model->view:" + o.toString()) })
                .filter(breaker)
                .compose(bindingContext.applyLifecycle<T>())
                .doOnSubscribe{ ViewModelBinder.LogBinding(property.value.toString() + " model->view") }
                .doOnTerminate{ ViewModelBinder.LogUnbinding(property.value.toString() + " model->view") }
                .doOnError{ e -> Log.d("Binding", "model->view error:" + e) }
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
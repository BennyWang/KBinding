package com.benny.library.neobinding.bind

import android.content.Context
import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.FragmentEvent
import com.trello.rxlifecycle.RxLifecycle
import rx.Observable

/**
 * Created by benny on 11/17/15.
 */

public class BindingContext<T>(val context: Context, val lifecycle: Observable<T>, val event: T) {

    fun <R> applyLifecycle(): Observable.Transformer<R, R> {
        when(event) {
            is FragmentEvent ->
                return RxLifecycle.bindUntilFragmentEvent<R>(lifecycle as Observable<FragmentEvent>, event)
            is ActivityEvent ->
                return RxLifecycle.bindUntilActivityEvent<R>(lifecycle as Observable<ActivityEvent>, event)
        }
        return Observable.Transformer<R, R> {source -> source}
    }
}
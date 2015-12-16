package com.benny.library.neobinding.bind

import android.content.Context
import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.FragmentEvent
import com.trello.rxlifecycle.RxLifecycle
import rx.Observable

/**
 * Created by benny on 11/17/15.
 */

public interface BindingContext {
    val context: Context
    fun <R> applyLifecycle(): Observable.Transformer<R, R>
}
package com.benny.library.neobinding.bind

import android.content.Context
import rx.Observable

/**
 * Created by benny on 11/17/15.
 */

public interface BindingContext {
    val context: Context
    fun <T> applyLifecycle(): Observable.Transformer<T, T>
}
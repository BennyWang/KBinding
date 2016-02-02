@file:Suppress("UNUSED_PARAMETER")

package com.benny.library.kbinding.support.v4.bindings

import android.support.v4.widget.SwipeRefreshLayout
import com.benny.library.kbinding.bind.PropertyBinding
import com.benny.library.kbinding.bind.commandBinding
import com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout
import rx.functions.Action1

/**
 * Created by benny on 12/16/15.
 */

fun SwipeRefreshLayout.refresh(path: String) : PropertyBinding = commandBinding(path, RxSwipeRefreshLayout.refreshes(this).map { Unit }, Action1 { this.isRefreshing = !it })



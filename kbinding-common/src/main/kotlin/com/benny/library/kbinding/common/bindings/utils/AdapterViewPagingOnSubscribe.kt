package com.benny.library.kbinding.common.bindings.utils

import android.view.View
import com.benny.library.kbinding.common.adapter.AdapterPagingListener
import com.jakewharton.rxbinding.internal.MainThreadSubscription
import rx.Observable
import rx.Subscriber

/**
 * Created by benny on 12/26/15.
 */

class AdapterViewPagingOnSubscribe(val view: View) : Observable.OnSubscribe<Unit> {
    override fun call(subscriber: Subscriber<in Unit>) {
        val pagingListener = object : AdapterPagingListener {
            override fun onLoadPage(previous: Any?, position: Int) {
                if (subscriber.isUnsubscribed) return

                subscriber.onNext(Unit);
            }
        }
        view.tag = pagingListener
        subscriber.add(object : MainThreadSubscription() {
            override fun onUnsubscribe() {
                view.tag = null;
            }
        });
    }
}
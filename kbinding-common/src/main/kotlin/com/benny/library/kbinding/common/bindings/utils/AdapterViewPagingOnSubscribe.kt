package com.benny.library.kbinding.common.bindings.utils

import android.view.View
import com.benny.library.kbinding.common.adapter.AdapterPagingListener
import com.jakewharton.rxbinding.internal.MainThreadSubscription
import rx.Observable
import rx.Subscriber

/**
 * Created by benny on 12/26/15.
 */

class AdapterViewPagingOnSubscribe(val view: View) : Observable.OnSubscribe<Pair<Int, Any?>> {
    override fun call(subscriber: Subscriber<in Pair<Int, Any?>>) {
        val pagingListener = object : AdapterPagingListener {
            override fun onLoadPage(previous: Any?, position: Int) {
                if (subscriber.isUnsubscribed) return

                subscriber.onNext(Pair(position, previous));
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
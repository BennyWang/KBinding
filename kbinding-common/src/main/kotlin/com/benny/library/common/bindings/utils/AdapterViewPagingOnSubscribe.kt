package com.benny.library.common.bindings.utils

import android.view.View
import com.benny.library.kbinding.adapter.AdapterPagingListener
import com.jakewharton.rxbinding.internal.MainThreadSubscription
import rx.Observable
import rx.Subscriber

/**
 * Created by benny on 12/26/15.
 */
class AdapterViewPagingOnSubscribe(val view: View) : Observable.OnSubscribe<Void> {
    override fun call(subscriber: Subscriber<in Void>) {
        val pagingListener = object : AdapterPagingListener {
            override fun onLoadPage(previous: Any?, position: Int) {
                if (subscriber.isUnsubscribed) return

                subscriber.onNext(null);
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
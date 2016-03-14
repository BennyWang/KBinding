package com.benny.library.kbinding.adapterview.bindings.utils

import android.widget.ListView
import com.benny.library.autoadapter.listener.AdapterPagingListener
import com.benny.library.kbinding.adapterview.bindings.setPagingListener
import com.jakewharton.rxbinding.internal.MainThreadSubscription
import rx.Observable
import rx.Subscriber

/**
 * Created by benny on 12/26/15.
 */

class ListViewPagingOnSubscribe(val view: ListView) : Observable.OnSubscribe<Pair<Int, Any?>> {
    override fun call(subscriber: Subscriber<in Pair<Int, Any?>>) {
        val pagingListener = AdapterPagingListener<Any?> { receiver, previous, position ->
            if (subscriber.isUnsubscribed) return@AdapterPagingListener

            subscriber.onNext(Pair(position, previous));
        }
        view.setPagingListener(pagingListener)
        subscriber.add(object : MainThreadSubscription() {
            override fun onUnsubscribe() {
                view.setPagingListener(null)
            }
        });
    }
}
package com.benny.library.kbinding.common.bindings.utils

import android.support.v7.widget.RecyclerView
import android.view.View
import com.benny.library.kbinding.common.adapter.AdapterPagingListener
import com.benny.library.kbinding.support.v7.bindings.PAGING_LISTENER
import com.benny.library.kbinding.support.v7.bindings.setPagingListener
import com.jakewharton.rxbinding.internal.MainThreadSubscription
import rx.Observable
import rx.Subscriber
import java.util.*

/**
 * Created by benny on 12/26/15.
 */

@Suppress("UNCHECKED_CAST")
class RecyclerViewPagingOnSubscribe(val view: RecyclerView) : Observable.OnSubscribe<Pair<Int, Any?>> {
    override fun call(subscriber: Subscriber<in Pair<Int, Any?>>) {
        val pagingListener = object : AdapterPagingListener {
            override fun onLoadPage(previous: Any?, position: Int) {
                if (subscriber.isUnsubscribed) return

                subscriber.onNext(Pair(position, previous));
            }
        }

        view.setPagingListener(pagingListener)
        subscriber.add(object : MainThreadSubscription() {
            override fun onUnsubscribe() {
                (view.tag as HashMap<String, Any?>).put(PAGING_LISTENER, null)
            }
        });
    }
}
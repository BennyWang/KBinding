package com.benny.library.neobinding.extension

import android.view.View
import com.benny.library.neobinding.adapter.AdapterPagingListener
import com.jakewharton.rxbinding.internal.MainThreadSubscription
import rx.Observable
import rx.Subscriber

/**
 * Created by benny on 12/18/15.
 */
class AdapterViewPagingOnSubscribe(val view: View) : Observable.OnSubscribe<Void>{
    override fun call(subscriber: Subscriber<in Void>) {
        val pagingListener = object : AdapterPagingListener {
            override fun onLoadPage(previous: Any?, position: Int) {
                if (!subscriber.isUnsubscribed) {
                    subscriber.onNext(null);
                }
            }
        }
        view.tag = pagingListener
        subscriber.add(object : MainThreadSubscription() {
            override protected fun onUnsubscribe() {
                view.tag = null;
            }
        });
    }
}
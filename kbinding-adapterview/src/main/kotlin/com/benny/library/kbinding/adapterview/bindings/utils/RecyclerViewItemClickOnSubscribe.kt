package com.benny.library.kbinding.adapterview.bindings.utils

import android.support.v7.widget.RecyclerView
import android.widget.AdapterView
import com.benny.library.kbinding.adapterview.bindings.setOnItemClickListener
import com.jakewharton.rxbinding.internal.MainThreadSubscription
import com.jakewharton.rxbinding.internal.Preconditions
import rx.Observable
import rx.Subscriber

/**
 * Created by benny on 2/3/16.
 */

class RecyclerViewItemClickOnSubscribe(val view: RecyclerView) : Observable.OnSubscribe<Int> {
    override fun call(subscriber: Subscriber<in Int>) {
        Preconditions.checkUiThread()
        val listener = AdapterView.OnItemClickListener { parent, view, position, id ->
            if (!subscriber.isUnsubscribed) {
                subscriber.onNext(position)
            }
        }
        view.setOnItemClickListener(listener)
        subscriber.add(object : MainThreadSubscription() {
            override fun onUnsubscribe() {
                view.setOnItemClickListener(null)
            }
        })
    }
}
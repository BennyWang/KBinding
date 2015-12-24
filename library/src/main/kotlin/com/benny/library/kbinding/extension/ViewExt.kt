package com.benny.library.kbinding.extension

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import org.jetbrains.anko.*
import rx.Observable
import rx.functions.Action1

import com.benny.library.kbinding.adapter.AdapterPagingListener
import com.benny.library.kbinding.adapter.BaseListAdapter
import com.benny.library.kbinding.adapter.BaseRecyclerPagingAdapter
import com.benny.library.kbinding.view.ViewBinderComponent
import com.jakewharton.rxbinding.internal.MainThreadSubscription
import rx.Subscriber

/**
 * Created by benny on 12/16/15.
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
            override protected fun onUnsubscribe() {
                view.tag = null;
            }
        });
    }
}

fun ViewBinderComponent<out Activity>.setContentView(activity: Activity) =
        createViewBinder(AnkoContextImpl(activity, activity, true))

public var TextView.textColorResource: Int
    get() = throw AnkoException("'textColorResource' property does not have a getter")
    set(v) = setTextColor(context.resources.getColor(v))

public var TextView.textStyle: Int
    get() = throw AnkoException("'android.widget.TextView.textStyle' property does not have a getter")
    set(v) = setTypeface(typeface, v)

public fun RecyclerView.swapAdapter() : Action1<RecyclerView.Adapter<RecyclerView.ViewHolder>> {
    return Action1 { it ->
        this.swapAdapter(it, false)
        if (adapter is BaseRecyclerPagingAdapter<*> && this.tag is AdapterPagingListener)
            (adapter as BaseRecyclerPagingAdapter<*>).pagingListener = this.tag as AdapterPagingListener
    }
}

public fun ListView.swapAdapter(): Action1<ListAdapter> {
    return Action1 { it ->
        val adapter = this.adapter
        if (adapter != null && adapter is BaseListAdapter<*> && it is BaseListAdapter<*>) {
            adapter.swap(it)
        } else {
            this.adapter = it
        }
        if (this.adapter is BaseRecyclerPagingAdapter<*> && this.tag is AdapterPagingListener)
            (this.adapter as BaseRecyclerPagingAdapter<*>).pagingListener = this.tag as AdapterPagingListener
    }
}

public fun ListView.paging(): Observable<Unit> {
    return Observable.create(AdapterViewPagingOnSubscribe(this)).map { Unit };
}

public fun RecyclerView.paging(): Observable<Unit> {
    return Observable.create(AdapterViewPagingOnSubscribe(this)).map { Unit };
}
@file:Suppress("UNUSED_PARAMETER")

package com.benny.library.kbinding.adapterview.bindings

import android.widget.ListAdapter
import android.widget.ListView
import com.benny.library.autoadapter.AutoListAdapter
import com.benny.library.autoadapter.AutoListPagingAdapter
import com.benny.library.autoadapter.listener.AdapterPagingCompleteListener
import com.benny.library.autoadapter.listener.AdapterPagingListener
import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.adapterview.bindings.utils.ListViewPagingOnSubscribe
import com.benny.library.kbinding.converter.OneWayConverter
import com.jakewharton.rxbinding.widget.RxAdapterView
import rx.Observable
import rx.functions.Action1

/**
 * Created by benny on 12/16/15.
 */

@Suppress("UNCHECKED_CAST")
fun ListView.setPagingListener(pagingListener: AdapterPagingListener<*>?) {
    tag = pagingListener
    if(pagingListener != null) (adapter as? AutoListPagingAdapter<Any>)?.setPagingListener(pagingListener as AdapterPagingListener<Any>)
}

@Suppress("UNCHECKED_CAST")
fun ListView.swapAdapter(): Action1<ListAdapter> {
    return Action1 { it ->
        val adapter = this.adapter
        if (adapter is AutoListAdapter<*> && it is AutoListAdapter<*>) {
            (adapter as AutoListAdapter<Any>).swap(it as AutoListAdapter<Any>)
        } else {
            this.adapter = it
        }

        if (it is AutoListPagingAdapter<*>) {
            (it as AutoListPagingAdapter<Any>).setPagingListener(this.tag as? AdapterPagingListener<Any>)
        }
    }
}

fun ListView.paging(): Observable<Pair<Int, Any?>> = Observable.create(ListViewPagingOnSubscribe(this))

//Event

fun ListView.paging(path: String) : PropertyBinding = commandBinding(path, paging(), Action1 { (this.adapter as? AdapterPagingCompleteListener)?.onPagingComplete(!it) })
fun ListView.itemClick(path: String) : PropertyBinding = commandBinding(path, RxAdapterView.itemClicks(this), Action1 {})

//Property

fun ListView.adapter(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<*, ListAdapter>) : PropertyBinding = oneWayPropertyBinding(paths, swapAdapter(), false, converter)
fun ListView.adapter(vararg paths: String, mode: OneTime, converter: OneWayConverter<*, ListAdapter>) : PropertyBinding = oneWayPropertyBinding(paths, swapAdapter(), true, converter)

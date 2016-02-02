@file:Suppress("UNUSED_PARAMETER")

package com.benny.library.kbinding.common.bindings

import android.widget.ListAdapter
import android.widget.ListView
import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.common.adapter.AdapterPagingListener
import com.benny.library.kbinding.common.adapter.BaseListAdapter
import com.benny.library.kbinding.common.adapter.BaseListPagingAdapter
import com.benny.library.kbinding.common.bindings.utils.AdapterViewPagingOnSubscribe
import com.benny.library.kbinding.converter.EmptyOneWayConverter
import com.benny.library.kbinding.converter.OneWayConverter
import com.jakewharton.rxbinding.widget.RxAdapterView
import rx.Observable
import rx.functions.Action1

/**
 * Created by benny on 12/16/15.
 */

@Suppress("UNCHECKED_CAST") fun ListView.swapAdapter(): Action1<ListAdapter> {
    return Action1 { it ->
        val adapter = this.adapter
        if (adapter is BaseListAdapter<*> && it is BaseListAdapter<*>) {
            (adapter as BaseListAdapter<Any>).swap(it as BaseListAdapter<Any>)
        }
        else {
            if(it is BaseListPagingAdapter<*>) {
                it.pagingListener = this.tag as? AdapterPagingListener
            }
            this.adapter = it
        }
    }
}

fun ListView.paging(): Observable<Pair<Int, Any?> > = Observable.create(AdapterViewPagingOnSubscribe(this))

//Event

fun ListView.paging(path: String) : PropertyBinding = commandBinding(path, paging(), Action1 { (this.adapter as? BaseListPagingAdapter<*>)?.loadComplete(!it) })
fun ListView.itemClick(path: String) : PropertyBinding = commandBinding(path, RxAdapterView.itemClicks(this), Action1 {})

//Property

fun ListView.adapter(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<ListAdapter> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, swapAdapter(), false, converter)
fun ListView.adapter(vararg paths: String, mode: OneTime, converter: OneWayConverter<ListAdapter> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, swapAdapter(), true, converter)

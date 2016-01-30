@file:Suppress("UNUSED_PARAMETER")

package com.benny.library.common.bindings

import android.support.v7.widget.RecyclerView
import android.widget.ListAdapter
import android.widget.ListView
import rx.Observable
import rx.functions.Action1

import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.converter.*
import com.benny.library.common.bindings.utils.AdapterViewPagingOnSubscribe
import com.benny.library.kbinding.adapter.AdapterPagingListener
import com.benny.library.kbinding.adapter.BaseListAdapter
import com.benny.library.kbinding.adapter.BaseRecyclerPagingAdapter
import com.jakewharton.rxbinding.widget.RxAdapterView

/**
 * Created by benny on 12/16/15.
 */

fun RecyclerView.swapAdapter() : Action1<RecyclerView.Adapter<RecyclerView.ViewHolder>> {
    return Action1 { it ->
        this.swapAdapter(it, false)
        if (adapter is BaseRecyclerPagingAdapter<*> && this.tag is AdapterPagingListener)
            (adapter as BaseRecyclerPagingAdapter<*>).pagingListener = this.tag as AdapterPagingListener
    }
}

@Suppress("UNCHECKED_CAST") fun ListView.swapAdapter(): Action1<ListAdapter> {
    return Action1 { it ->
        val adapter = this.adapter
        if (adapter != null && adapter is BaseListAdapter<*> && it is BaseListAdapter<*>) {
            (adapter as BaseListAdapter<Any>).swap(it as BaseListAdapter<Any>)
        } else {
            this.adapter = it
        }
        if (this.adapter is BaseRecyclerPagingAdapter<*> && this.tag is AdapterPagingListener)
            (this.adapter as BaseRecyclerPagingAdapter<*>).pagingListener = this.tag as AdapterPagingListener
    }
}

fun ListView.paging(): Observable<Unit> = Observable.create(AdapterViewPagingOnSubscribe(this)).map { Unit }

fun RecyclerView.paging(): Observable<Unit> = Observable.create(AdapterViewPagingOnSubscribe(this)).map { Unit }

//Event

fun ListView.paging(path: String) : PropertyBinding = commandBinding(path, paging(), Action1 {})
fun ListView.itemClick(path: String) : PropertyBinding = commandBinding(path, RxAdapterView.itemClicks(this), Action1 {})

fun RecyclerView.paging(path: String) : PropertyBinding = commandBinding(path, paging(), Action1 {})

//Property

fun ListView.adapter(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<ListAdapter> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, swapAdapter(), false, converter)
fun ListView.adapter(vararg paths: String, mode: OneTime, converter: OneWayConverter<ListAdapter> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, swapAdapter(), true, converter)

fun RecyclerView.adapter(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<RecyclerView.Adapter<RecyclerView.ViewHolder>> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, swapAdapter(), false, converter)
fun RecyclerView.adapter(vararg paths: String, mode: OneTime, converter: OneWayConverter<RecyclerView.Adapter<RecyclerView.ViewHolder>> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, swapAdapter(), true, converter)


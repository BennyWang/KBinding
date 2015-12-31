package com.benny.library.kbinding.dsl

import android.support.v7.widget.RecyclerView
import android.widget.ListAdapter
import android.widget.ListView
import rx.Observable
import rx.functions.Action1

import com.benny.library.kbinding.adapter.AdapterPagingListener
import com.benny.library.kbinding.adapter.BaseListAdapter
import com.benny.library.kbinding.adapter.BaseRecyclerPagingAdapter
import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.converter.*
import com.benny.library.kbinding.dsl.utils.AdapterViewPagingOnSubscribe
import com.jakewharton.rxbinding.widget.itemClicks

/**
 * Created by benny on 12/16/15.
 */

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

fun ListView.paging(): Observable<Unit> = Observable.create(AdapterViewPagingOnSubscribe(this)).map { Unit }

fun RecyclerView.paging(): Observable<Unit> = Observable.create(AdapterViewPagingOnSubscribe(this)).map { Unit }

//Event

fun ListView.paging(path: String) : PropertyBinding = commandBinding(path, paging(), Action1 {})
fun ListView.itemClick(path: String) : PropertyBinding = commandBinding(path, itemClicks(), Action1 {})

fun RecyclerView.paging(path: String) : PropertyBinding = commandBinding(path, paging(), Action1 {})

//Property

fun ListView.adapter(path: String, mode: OneWay = BindingMode.OneWay, oneTime: Boolean = false, converter: OneWayConverter<ListAdapter> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, swapAdapter(), oneTime, converter)
fun ListView.adapter(paths: List<String>, oneTime: Boolean = false, converter: MultipleConverter<ListAdapter>) : PropertyBinding = multiplePropertyBinding(paths, swapAdapter(), oneTime, converter)

fun RecyclerView.adapter(path: String, mode: OneWay = BindingMode.OneWay, oneTime: Boolean = false, converter: OneWayConverter<RecyclerView.Adapter<RecyclerView.ViewHolder>> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, swapAdapter(), oneTime, converter)
fun RecyclerView.adapter(paths: List<String>, oneTime: Boolean = false, converter: MultipleConverter<RecyclerView.Adapter<RecyclerView.ViewHolder>>) : PropertyBinding = multiplePropertyBinding(paths, swapAdapter(), oneTime, converter)


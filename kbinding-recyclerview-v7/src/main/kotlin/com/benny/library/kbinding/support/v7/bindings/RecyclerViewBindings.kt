@file:Suppress("UNUSED_PARAMETER")

package com.benny.library.kbinding.support.v7.bindings

import android.support.v7.widget.RecyclerView
import android.widget.AdapterView
import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.common.adapter.AdapterPagingListener
import com.benny.library.kbinding.common.bindings.utils.RecyclerViewPagingOnSubscribe
import com.benny.library.kbinding.converter.EmptyOneWayConverter
import com.benny.library.kbinding.converter.OneWayConverter
import com.benny.library.kbinding.support.v7.adapter.BaseRecyclerAdapter
import com.benny.library.kbinding.support.v7.adapter.BaseRecyclerPagingAdapter
import com.benny.library.kbinding.support.v7.bindings.utils.RecyclerViewItemClickOnSubscribe
import rx.Observable
import rx.functions.Action1
import java.util.*

/**
 * Created by benny on 12/16/15.
 */

val PAGING_LISTENER = "PAGING_LISTENER"
val ON_ITEM_CLICK_LISTENER = "ON_ITEM_CLICK_LISTENER"

@Suppress("UNCHECKED_CAST")
fun RecyclerView.setOnItemClickListener(onItemClickListener: AdapterView.OnItemClickListener?) {
    if(tag !is HashMap<*, *>) tag = HashMap<String, Any?>()
    (tag as HashMap<String, Any?>).put(ON_ITEM_CLICK_LISTENER, onItemClickListener)
    if(onItemClickListener != null) (adapter as? BaseRecyclerAdapter<*>)?.onItemClickListener = onItemClickListener
}

@Suppress("UNCHECKED_CAST")
fun RecyclerView.setPagingListener(pagingListener: AdapterPagingListener?) {
    if(tag !is HashMap<*, *>) tag = HashMap<String, Any?>()
    (tag as HashMap<String, Any?>).put(PAGING_LISTENER, pagingListener)

    if(pagingListener != null) (adapter as? BaseRecyclerPagingAdapter<*>)?.pagingListener = pagingListener
}

fun RecyclerView.itemClicks(): Observable<Int> {
    return Observable.create(RecyclerViewItemClickOnSubscribe(this))
}

@Suppress("UNCHECKED_CAST")
fun RecyclerView.swapAdapter() : Action1<RecyclerView.Adapter<RecyclerView.ViewHolder>> {
    return Action1 { it ->
        this.swapAdapter(it, false)
        if(tag is HashMap<*, *>) {
            (adapter as? BaseRecyclerPagingAdapter<*>)?.pagingListener = (this.tag as? HashMap<String, AdapterPagingListener?>)?.get(PAGING_LISTENER)
            (adapter as? BaseRecyclerAdapter<*>)?.onItemClickListener = (this.tag as? HashMap<String, AdapterView.OnItemClickListener?>)?.get(ON_ITEM_CLICK_LISTENER)
        }
    }
}

fun RecyclerView.paging(): Observable<Pair<Int, Any?>> = Observable.create(RecyclerViewPagingOnSubscribe(this))

//Event

fun RecyclerView.paging(path: String) : PropertyBinding = commandBinding(path, paging(), Action1 { (this.adapter as? BaseRecyclerPagingAdapter<*>)?.loadComplete(!it) })
fun RecyclerView.itemClick(path: String) : PropertyBinding = commandBinding(path, itemClicks(), Action1 {})
//Property

fun RecyclerView.adapter(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<RecyclerView.Adapter<RecyclerView.ViewHolder>> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, swapAdapter(), false, converter)
fun RecyclerView.adapter(vararg paths: String, mode: OneTime, converter: OneWayConverter<RecyclerView.Adapter<RecyclerView.ViewHolder>> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, swapAdapter(), true, converter)


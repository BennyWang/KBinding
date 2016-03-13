@file:Suppress("UNUSED_PARAMETER")

package com.benny.library.kbinding.adapterview.bindings

import rx.Observable
import rx.functions.Action1
import java.util.*
import android.support.v7.widget.RecyclerView
import android.widget.AdapterView
import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.adapterview.bindings.utils.RecyclerViewPagingOnSubscribe
import com.benny.library.kbinding.converter.OneWayConverter
import com.benny.library.kbinding.adapterview.bindings.utils.RecyclerViewItemClickOnSubscribe
import com.benny.library.autoadapter.AutoRecyclerAdapter
import com.benny.library.autoadapter.AutoRecyclerPagingAdapter
import com.benny.library.autoadapter.listener.AdapterPagingCompleteListener
import com.benny.library.autoadapter.listener.AdapterPagingListener

/**
 * Created by benny on 12/16/15.
 */

val PAGING_LISTENER = "PAGING_LISTENER"
val ON_ITEM_CLICK_LISTENER = "ON_ITEM_CLICK_LISTENER"

@Suppress("UNCHECKED_CAST")
fun RecyclerView.setOnItemClickListener(onItemClickListener: AdapterView.OnItemClickListener?) {
    if(tag !is HashMap<*, *>) tag = HashMap<String, Any?>()
    (tag as HashMap<String, Any?>).put(ON_ITEM_CLICK_LISTENER, onItemClickListener)
    if(onItemClickListener != null) (adapter as? AutoRecyclerAdapter<*>)?.setOnItemClickListener(onItemClickListener)
}

@Suppress("UNCHECKED_CAST")
fun RecyclerView.setPagingListener(pagingListener: AdapterPagingListener<*>?) {
    if(tag !is HashMap<*, *>) tag = HashMap<String, Any?>()
    (tag as HashMap<String, Any?>).put(PAGING_LISTENER, pagingListener)

    if(pagingListener != null) (adapter as? AutoRecyclerPagingAdapter<Any>)?.setPagingListener(pagingListener as AdapterPagingListener<Any>)
}

fun RecyclerView.itemClicks(): Observable<Int> {
    return Observable.create(RecyclerViewItemClickOnSubscribe(this))
}

@Suppress("UNCHECKED_CAST")
fun RecyclerView.swapAdapter() : Action1<RecyclerView.Adapter<RecyclerView.ViewHolder>> {
    return Action1 { it ->
        this.swapAdapter(it, false)
        if (tag is HashMap<*, *>) {
            (adapter as? AutoRecyclerPagingAdapter<Any>)?.setPagingListener((this.tag as? HashMap<String, AdapterPagingListener<Any>?>)?.get(PAGING_LISTENER))
            (adapter as? AutoRecyclerAdapter<*>)?.setOnItemClickListener((this.tag as? HashMap<String, AdapterView.OnItemClickListener?>)?.get(ON_ITEM_CLICK_LISTENER))
        }
    }
}

fun RecyclerView.paging(): Observable<Pair<Int, Any?>> = Observable.create(RecyclerViewPagingOnSubscribe(this))

//Event

fun RecyclerView.paging(path: String) : PropertyBinding = commandBinding(path, paging(), Action1 { (this.adapter as? AdapterPagingCompleteListener)?.onPagingComplete(!it) })
fun RecyclerView.itemClick(path: String) : PropertyBinding = commandBinding(path, itemClicks(), Action1 {})
//Property

fun RecyclerView.adapter(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<*, RecyclerView.Adapter<RecyclerView.ViewHolder>>) : PropertyBinding = oneWayPropertyBinding(paths, swapAdapter(), false, converter)
fun RecyclerView.adapter(vararg paths: String, mode: OneTime, converter: OneWayConverter<*, RecyclerView.Adapter<RecyclerView.ViewHolder>>) : PropertyBinding = oneWayPropertyBinding(paths, swapAdapter(), true, converter)


package com.benny.library.kbinding.dsl

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.view.visibility
import com.jakewharton.rxbinding.widget.color
import com.jakewharton.rxbinding.widget.text
import com.jakewharton.rxbinding.widget.textChanges
import rx.Observable
import rx.functions.Action1

import com.benny.library.kbinding.adapter.AdapterPagingListener
import com.benny.library.kbinding.adapter.BaseListAdapter
import com.benny.library.kbinding.adapter.BaseRecyclerPagingAdapter
import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.converter.*
import com.benny.library.kbinding.dsl.utils.AdapterViewPagingOnSubscribe
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

public fun ListView.paging(): Observable<Unit> {
    return Observable.create(AdapterViewPagingOnSubscribe(this)).map { Unit };
}

public fun RecyclerView.paging(): Observable<Unit> {
    return Observable.create(AdapterViewPagingOnSubscribe(this)).map { Unit };
}

fun Drawable.level(): Action1<Int> = Action1 { t -> setLevel(t) }
fun View.background(): Action1<Drawable> = Action1 { t -> background = t }
fun TextView.textChanges2(): Observable<String> = textChanges().map { it.toString() }.skip(1)

// Event

fun View.click(path: String) : PropertyBinding {
    return BindingAssembler.commandBinding(path, clicks(), enabled())
}

fun ListView.paging(path: String) : PropertyBinding {
    return BindingAssembler.commandBinding(path, paging(), Action1 {})
}

fun RecyclerView.paging(path: String) : PropertyBinding {
    return BindingAssembler.commandBinding(path, paging(), Action1 {})
}

// Property

fun Drawable.level(path: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Int> = EmptyOneWayConverter<Int>()) : PropertyBinding {
    return BindingAssembler.oneWayPropertyBinding<Int, Any>(path, level(), converter)
}
fun Drawable.level(paths: List<String>, converter: MultipleConverter<Int>) : PropertyBinding = BindingAssembler.multiplePropertyBinding(paths, level(), converter)

fun View.enabled(path: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Boolean> = EmptyOneWayConverter<Boolean>()) : PropertyBinding {
    return BindingAssembler.oneWayPropertyBinding<Boolean, Any>(path, enabled(), converter)
}
fun View.enabled(paths: List<String>, converter: MultipleConverter<Boolean>) : PropertyBinding = BindingAssembler.multiplePropertyBinding(paths, enabled(), converter)

fun View.visibility(path: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Boolean> = EmptyOneWayConverter<Boolean>()) : PropertyBinding {
    return BindingAssembler.oneWayPropertyBinding<Boolean, Any>(path, visibility(), converter)
}
fun View.visibility(paths: List<String>, converter: MultipleConverter<Boolean>) : PropertyBinding = BindingAssembler.multiplePropertyBinding(paths, visibility(), converter)

fun View.background(path: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Drawable> = EmptyOneWayConverter<Drawable>()) : PropertyBinding {
    return BindingAssembler.oneWayPropertyBinding<Drawable, Any>(path, background(), converter)
}
fun TextView.background(paths: List<String>, converter: MultipleConverter<Drawable>) : PropertyBinding = BindingAssembler.multiplePropertyBinding(paths, background(), converter)

fun TextView.textColor(path: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Int> = EmptyOneWayConverter<Int>()) : PropertyBinding {
    return BindingAssembler.oneWayPropertyBinding<Int, Any>(path, color(), converter)
}
fun TextView.textColor(paths: List<String>, converter: MultipleConverter<Int>) : PropertyBinding = BindingAssembler.multiplePropertyBinding(paths, color(), converter)

fun TextView.text(path: String, mode: OneWay, converter: OneWayConverter<CharSequence> = EmptyOneWayConverter<CharSequence>()) : PropertyBinding {
    return BindingAssembler.oneWayPropertyBinding<CharSequence, Any>(path, text(), converter)
}

fun TextView.text(path: String, mode: OneWayToSource) : PropertyBinding = text<String>(path, mode)
fun <T> TextView.text(path: String, mode: OneWayToSource, converter: OneWayConverter<T> = EmptyOneWayConverter<T>()) : PropertyBinding {
    return BindingAssembler.oneWayPropertyBinding(path, textChanges2(), converter)
}
fun TextView.text(path: String, mode: TwoWay) : PropertyBinding = text<String>(path, mode)
fun <T> TextView.text(path: String, mode: TwoWay, converter: TwoWayConverter<String, T> = EmptyTwoWayConverter<String, T>()) : PropertyBinding {
    return BindingAssembler.twoWayPropertyBinding(path, textChanges2(), text(), converter)
}
fun TextView.text(paths: List<String>, converter: MultipleConverter<out CharSequence>) : PropertyBinding = BindingAssembler.multiplePropertyBinding(paths, text(), converter)

fun ListView.adapter(path: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<ListAdapter> = EmptyOneWayConverter<ListAdapter>()) : PropertyBinding {
    return BindingAssembler.oneWayPropertyBinding<ListAdapter, Any>(path, swapAdapter(), converter)
}
fun ListView.adapter(paths: List<String>, converter: MultipleConverter<ListAdapter>) : PropertyBinding = BindingAssembler.multiplePropertyBinding(paths, swapAdapter(), converter)

fun RecyclerView.adapter(path: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<RecyclerView.Adapter<RecyclerView.ViewHolder>> = EmptyOneWayConverter<RecyclerView.Adapter<RecyclerView.ViewHolder>>()) : PropertyBinding {
    return BindingAssembler.oneWayPropertyBinding<RecyclerView.Adapter<RecyclerView.ViewHolder>, Any>(path, swapAdapter(), converter)
}
fun RecyclerView.adapter(paths: List<String>, converter: MultipleConverter<RecyclerView.Adapter<RecyclerView.ViewHolder>>) : PropertyBinding = BindingAssembler.multiplePropertyBinding(paths, swapAdapter(), converter)


package com.benny.library.neobinding.extension

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import com.benny.library.neobinding.bind.BindingAssembler
import com.benny.library.neobinding.bind.BindingMode
import com.benny.library.neobinding.bind.PropertyBinding
import com.benny.library.neobinding.converter.EmptyOneWayConverter
import com.benny.library.neobinding.converter.MultipleConverter
import com.benny.library.neobinding.converter.OneWayConverter
import com.benny.library.neobinding.converter.TwoWayConverter
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.view.visibility
import com.jakewharton.rxbinding.widget.color
import com.jakewharton.rxbinding.widget.text
import com.jakewharton.rxbinding.widget.textChanges
import rx.functions.Action1

/**
 * Created by benny on 12/16/15.
 */

fun Drawable.level(): Action1<Int> = Action1 { t -> setLevel(t) }
fun Drawable.level(path: String, mode: BindingMode = BindingMode.OneWay, converter: Any? = EmptyOneWayConverter<Int>()) : PropertyBinding = when(mode) {
    BindingMode.OneWay -> BindingAssembler.oneWayPropertyBinding<Int, Any>(path, level(), converter as? OneWayConverter<Int>)
    BindingMode.OneWayToSource -> throw UnsupportedOperationException("OneWayToSource not allowed for drawable level")
    BindingMode.TwoWay -> throw UnsupportedOperationException("TwoWay not allowed for drawable level")
}
fun Drawable.level(paths: List<String>, converter: MultipleConverter<Int>) : PropertyBinding = BindingAssembler.multiplePropertyBinding(paths, level(), converter)

fun View.click(path: String) : PropertyBinding {
    return BindingAssembler.commandBinding(path, clicks(), enabled())
}

fun View.enabled(path: String, mode: BindingMode = BindingMode.OneWay, converter: Any? = EmptyOneWayConverter<Boolean>()) : PropertyBinding = when(mode) {
    BindingMode.OneWay -> BindingAssembler.oneWayPropertyBinding<Boolean, Any>(path, enabled(), converter as? OneWayConverter<Boolean>)
    BindingMode.OneWayToSource -> throw UnsupportedOperationException("OneWayToSource not allowed for enabled")
    BindingMode.TwoWay -> throw UnsupportedOperationException("TwoWay not allowed for enabled")
}
fun View.enabled(paths: List<String>, converter: MultipleConverter<Boolean>) : PropertyBinding = BindingAssembler.multiplePropertyBinding(paths, enabled(), converter)

fun View.visibility(path: String, mode: BindingMode = BindingMode.OneWay, converter: Any? = EmptyOneWayConverter<Boolean>()) : PropertyBinding = when(mode) {
    BindingMode.OneWay -> BindingAssembler.oneWayPropertyBinding<Boolean, Any>(path, visibility(), converter as? OneWayConverter<Boolean>)
    BindingMode.OneWayToSource -> throw UnsupportedOperationException("OneWayToSource not allowed for visibility")
    BindingMode.TwoWay -> throw UnsupportedOperationException("TwoWay not allowed for visibility")
}
fun View.visibility(paths: List<String>, converter: MultipleConverter<Boolean>) : PropertyBinding = BindingAssembler.multiplePropertyBinding(paths, visibility(), converter)

fun View.background(): Action1<Drawable> = Action1 { t -> background = t }
fun View.background(path: String, mode: BindingMode = BindingMode.OneWay, converter: Any? = EmptyOneWayConverter<Drawable>()) : PropertyBinding = when(mode) {
    BindingMode.OneWay -> BindingAssembler.oneWayPropertyBinding<Drawable, Any>(path, background(), converter as? OneWayConverter<Drawable>)
    BindingMode.OneWayToSource -> throw UnsupportedOperationException("OneWayToSource not allowed for visibility")
    BindingMode.TwoWay -> throw UnsupportedOperationException("TwoWay not allowed for visibility")
}
fun TextView.background(paths: List<String>, converter: MultipleConverter<Drawable>) : PropertyBinding = BindingAssembler.multiplePropertyBinding(paths, background(), converter)

fun TextView.textColor(path: String, mode: BindingMode = BindingMode.OneWay, converter: Any? = EmptyOneWayConverter<Int>()) : PropertyBinding = when(mode) {
    BindingMode.OneWay -> BindingAssembler.oneWayPropertyBinding<Int, Any>(path, color(), converter as? OneWayConverter<Int>)
    BindingMode.OneWayToSource -> throw UnsupportedOperationException("OneWayToSource not allowed for text color")
    BindingMode.TwoWay -> throw UnsupportedOperationException("TwoWay not allowed for text color")
}
fun TextView.textColor(paths: List<String>, converter: MultipleConverter<Int>) : PropertyBinding = BindingAssembler.multiplePropertyBinding(paths, color(), converter)

fun TextView.text(path: String, mode: BindingMode = BindingMode.OneWay, converter: Any? = EmptyOneWayConverter<CharSequence>()) : PropertyBinding = when(mode) {
    BindingMode.OneWay -> BindingAssembler.oneWayPropertyBinding<CharSequence, Any>(path, text(), converter as? OneWayConverter<CharSequence>)
    BindingMode.OneWayToSource -> BindingAssembler.oneWayPropertyBinding<String, Any>(path, textChanges().map { it.toString() }.skip(1), converter as? OneWayConverter<Any>)
    BindingMode.TwoWay -> BindingAssembler.twoWayPropertyBinding(path, textChanges().map { it.toString() }.skip(1), text(), converter as? TwoWayConverter<String, String>)
}
fun TextView.text(paths: List<String>, converter: MultipleConverter<out CharSequence>) : PropertyBinding = BindingAssembler.multiplePropertyBinding(paths, text(), converter)

fun ListView.adapter(path: String, mode: BindingMode = BindingMode.OneWay, converter: Any? = EmptyOneWayConverter<ListAdapter>()) : PropertyBinding = when(mode) {
    BindingMode.OneWay -> BindingAssembler.oneWayPropertyBinding<ListAdapter, Any>(path, swapAdapter(), converter as? OneWayConverter<ListAdapter>)
    BindingMode.OneWayToSource -> throw UnsupportedOperationException("OneWayToSource not allowed for adapter")
    BindingMode.TwoWay -> throw UnsupportedOperationException("TwoWay not allowed for adapter")
}
fun ListView.adapter(paths: List<String>, converter: MultipleConverter<ListAdapter>) : PropertyBinding = BindingAssembler.multiplePropertyBinding(paths, swapAdapter(), converter)

fun RecyclerView.adapter(path: String, mode: BindingMode = BindingMode.OneWay, converter: Any? = EmptyOneWayConverter<RecyclerView.Adapter<RecyclerView.ViewHolder>>()) : PropertyBinding = when(mode) {
    BindingMode.OneWay -> BindingAssembler.oneWayPropertyBinding<RecyclerView.Adapter<RecyclerView.ViewHolder>, Any>(path, swapAdapter(), converter as? OneWayConverter<RecyclerView.Adapter<RecyclerView.ViewHolder>>)
    BindingMode.OneWayToSource -> throw UnsupportedOperationException("OneWayToSource not allowed for adapter")
    BindingMode.TwoWay -> throw UnsupportedOperationException("TwoWay not allowed for adapter")
}
fun RecyclerView.adapter(paths: List<String>, converter: MultipleConverter<RecyclerView.Adapter<RecyclerView.ViewHolder>>) : PropertyBinding = BindingAssembler.multiplePropertyBinding(paths, swapAdapter(), converter)

fun ListView.paging(path: String) : PropertyBinding {
    return BindingAssembler.commandBinding(path, paging(), Action1{})
}

fun RecyclerView.paging(path: String) : PropertyBinding {
    return BindingAssembler.commandBinding(path, paging(), Action1{})
}
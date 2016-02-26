@file:Suppress("UNUSED_PARAMETER")

package com.benny.library.kbinding.common.bindings

import android.widget.TextView
import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.converter.*
import com.jakewharton.rxbinding.widget.RxTextView
import org.jetbrains.anko.AnkoException
import rx.Observable

/**
 * Created by benny on 12/16/15.
 */
var TextView.textWeight: Int
    get() = throw AnkoException("'android.widget.TextView.textWeight' property does not have a getter")
    set(v) = setTypeface(typeface, v)

var TextView.textColorResource: Int
    get() = throw AnkoException("'android.widget.TextView.textWeight' property does not have a getter")
    set(v) = setTextColor(context.resources.getColor(v))

fun TextView.textChanges(): Observable<String> = RxTextView.textChanges(this).map { it.toString() }.skip(1)

//For TextView
fun TextView.textColor(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<*, Int> = EmptyOneWayConverter1()) : PropertyBinding = oneWayPropertyBinding(paths, RxTextView.color(this), false, converter)
fun TextView.textColor(vararg paths: String, mode: OneTime = BindingMode.OneTime, converter: OneWayConverter<*, Int> = EmptyOneWayConverter1()) : PropertyBinding = oneWayPropertyBinding(paths, RxTextView.color(this), true, converter)

fun TextView.text(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<*, out CharSequence> = EmptyOneWayConverter1()) : PropertyBinding = oneWayPropertyBinding(paths, RxTextView.text(this), false, converter)
fun TextView.text(vararg paths: String, mode: OneTime, converter: OneWayConverter<*, out CharSequence> = EmptyOneWayConverter1()) : PropertyBinding = oneWayPropertyBinding(paths, RxTextView.text(this), true, converter)
fun TextView.text(path: String, mode: OneWayToSource, converter: OneWayConverter<String, *> = EmptyOneWayConverter2<String, Any?>()) : PropertyBinding = oneWayPropertyBinding(path, textChanges(), converter)
fun TextView.text(path: String, mode: TwoWay, converter: TwoWayConverter<String, Any?> = EmptyTwoWayConverter()) : PropertyBinding = twoWayPropertyBinding(path, textChanges(), RxTextView.text(this), converter)



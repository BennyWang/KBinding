package com.benny.library.kbinding.dsl

import android.widget.TextView
import com.jakewharton.rxbinding.widget.color
import com.jakewharton.rxbinding.widget.text
import com.jakewharton.rxbinding.widget.textChanges
import rx.Observable
import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.converter.*

/**
 * Created by benny on 12/16/15.
 */


fun TextView.textChanges2(): Observable<String> = textChanges().map { it.toString() }.skip(1)

//For TextView
fun TextView.textColor(path: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Int> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, color(), converter)
fun TextView.textColor(paths: List<String>, converter: MultipleConverter<Int>) : PropertyBinding = multiplePropertyBinding(paths, color(), converter)

fun TextView.text(path: String, mode: OneWay, converter: OneWayConverter<CharSequence> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, text(), converter)
fun TextView.text(path: String, mode: OneWayToSource, converter: OneWayConverter<*> = EmptyOneWayConverter<String>()) : PropertyBinding = oneWayPropertyBinding(path, textChanges2(), converter)
fun TextView.text(path: String, mode: TwoWay, converter: TwoWayConverter<String, *> = EmptyTwoWayConverter<String, String>()) : PropertyBinding = twoWayPropertyBinding(path, textChanges2(), text(), converter)
fun TextView.text(paths: List<String>, converter: MultipleConverter<out CharSequence>) : PropertyBinding = multiplePropertyBinding(paths, text(), converter)



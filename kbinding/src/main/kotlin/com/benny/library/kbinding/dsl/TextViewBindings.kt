package com.benny.library.kbinding.dsl

import android.widget.TextView
import rx.Observable
import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.converter.*
import com.jakewharton.rxbinding.widget.RxTextView

/**
 * Created by benny on 12/16/15.
 */
fun TextView.textChanges(): Observable<String> = RxTextView.textChanges(this).map { it.toString() }.skip(1)

//For TextView
fun TextView.textColor(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Int> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, RxTextView.color(this), false, converter)
fun TextView.textColor(vararg paths: String, mode: OneTime = BindingMode.OneTime, converter: OneWayConverter<Int> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, RxTextView.color(this), true, converter)

fun TextView.text(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<out CharSequence> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, RxTextView.text(this), false, converter)
fun TextView.text(vararg paths: String, mode: OneTime, converter: OneWayConverter<out CharSequence> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, RxTextView.text(this), true, converter)
fun TextView.text(path: String, mode: OneWayToSource, converter: OneWayConverter<*> = EmptyOneWayConverter<String>()) : PropertyBinding = oneWayPropertyBinding(path, textChanges(), converter)
fun TextView.text(path: String, mode: TwoWay, converter: TwoWayConverter<String, *> = EmptyTwoWayConverter<String, String>()) : PropertyBinding = twoWayPropertyBinding(path, textChanges(), RxTextView.text(this), converter)



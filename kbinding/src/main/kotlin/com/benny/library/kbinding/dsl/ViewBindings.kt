package com.benny.library.kbinding.dsl

import android.graphics.drawable.Drawable
import android.view.View
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.view.visibility
import rx.functions.Action1

import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.converter.*

/**
 * Created by benny on 12/16/15.
 */


fun Drawable.level(): Action1<Int> = Action1 { t -> setLevel(t) }

fun View.background(): Action1<Drawable> = Action1 { t -> background = t }

fun View.backgroundColor(): Action1<Int> = Action1 { t -> setBackgroundColor(t) }

// Event

fun View.click(path: String) : PropertyBinding = commandBinding(path, clicks(), enabled())

// Property

fun Drawable.level(path: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Int> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, level(), converter)
fun Drawable.level(paths: List<String>, converter: MultipleConverter<Int>) : PropertyBinding = multiplePropertyBinding(paths, level(), converter)

fun View.enabled(path: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Boolean> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, enabled(), converter)
fun View.enabled(paths: List<String>, converter: MultipleConverter<Boolean>) : PropertyBinding = multiplePropertyBinding(paths, enabled(), converter)

fun View.visibility(path: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Boolean> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, visibility(), converter)
fun View.visibility(paths: List<String>, converter: MultipleConverter<Boolean>) : PropertyBinding = multiplePropertyBinding(paths, visibility(), converter)

fun View.background(path: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Drawable> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, background(), converter)
fun View.background(paths: List<String>, converter: MultipleConverter<Drawable>) : PropertyBinding = multiplePropertyBinding(paths, background(), converter)

fun View.backgroundColor(path: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Int> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, backgroundColor(), converter)
fun View.backgroundColor(paths: List<String>, converter: MultipleConverter<Int>) : PropertyBinding = multiplePropertyBinding(paths, backgroundColor(), converter)


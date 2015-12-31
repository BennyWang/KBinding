package com.benny.library.kbinding.dsl

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewManager
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.view.visibility
import rx.functions.Action1

import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.converter.*
import com.benny.library.kbinding.view.ViewComponent

/**
 * Created by benny on 12/16/15.
 */


fun Drawable.level(): Action1<Int> = Action1 { t -> setLevel(t) }

fun View.background(): Action1<Drawable> = Action1 { t -> background = t }

fun View.backgroundColor(): Action1<Int> = Action1 { t -> setBackgroundColor(t) }

// Event

fun View.click(path: String) : PropertyBinding = commandBinding(path, clicks(), enabled())

// Property

fun Drawable.level(path: String, mode: OneWay = BindingMode.OneWay, oneTime: Boolean = false, converter: OneWayConverter<Int> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, level(), oneTime, converter)
fun Drawable.level(paths: List<String>, oneTime: Boolean = false, converter: MultipleConverter<Int>) : PropertyBinding = multiplePropertyBinding(paths, level(), oneTime, converter)

fun <T> View.raw(path: String, mode: OneWay = BindingMode.OneWay, oneTime: Boolean = false, action: Action1<T>, converter: OneWayConverter<out T> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, action, oneTime, converter)
fun <T> View.raw(paths: List<String>, oneTime: Boolean = false, action: Action1<T>, converter: MultipleConverter<out T>) : PropertyBinding = multiplePropertyBinding(paths, action, oneTime, converter)

fun View.enabled(path: String, mode: OneWay = BindingMode.OneWay, oneTime: Boolean = false, converter: OneWayConverter<Boolean> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, enabled(), oneTime, converter)
fun View.enabled(paths: List<String>, oneTime: Boolean = false, converter: MultipleConverter<Boolean>) : PropertyBinding = multiplePropertyBinding(paths, enabled(), oneTime, converter)

fun View.visibility(path: String, mode: OneWay = BindingMode.OneWay, oneTime: Boolean = false, converter: OneWayConverter<Boolean> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, visibility(), oneTime, converter)
fun View.visibility(paths: List<String>, oneTime: Boolean = false, converter: MultipleConverter<Boolean>) : PropertyBinding = multiplePropertyBinding(paths, visibility(), oneTime, converter)

fun View.background(path: String, mode: OneWay = BindingMode.OneWay, oneTime: Boolean = false, converter: OneWayConverter<Drawable> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, background(), oneTime, converter)
fun View.background(paths: List<String>, oneTime: Boolean = false, converter: MultipleConverter<Drawable>) : PropertyBinding = multiplePropertyBinding(paths, background(), oneTime, converter)

fun View.backgroundColor(path: String, mode: OneWay = BindingMode.OneWay, oneTime: Boolean = false, converter: OneWayConverter<Int> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, backgroundColor(), oneTime, converter)
fun View.backgroundColor(paths: List<String>, oneTime: Boolean = false, converter: MultipleConverter<Int>) : PropertyBinding = multiplePropertyBinding(paths, backgroundColor(), oneTime, converter)


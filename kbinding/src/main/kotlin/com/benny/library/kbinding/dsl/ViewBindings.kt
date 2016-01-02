package com.benny.library.kbinding.dsl

import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
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

fun View.fadeOut() {
    val fadeOut: Animation = AlphaAnimation(1f, 0f);
    fadeOut.interpolator = AccelerateInterpolator();
    fadeOut.duration = 100;

    fadeOut.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation?) {
            visibility = View.GONE
        }
        override fun onAnimationStart(animation: Animation?) { }
        override fun onAnimationRepeat(animation: Animation?) { }
    });
    startAnimation(fadeOut);
}

// Event

fun View.click(path: String) : PropertyBinding = commandBinding(path, clicks(), enabled())

// Property

fun Drawable.level(path: String, mode: OneWay = BindingMode.OneWay, oneTime: Boolean = false, converter: OneWayConverter<Int> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, level(), oneTime, converter)
fun Drawable.level(paths: List<String>, oneTime: Boolean = false, converter: MultipleConverter<Int>) : PropertyBinding = multiplePropertyBinding(paths, level(), oneTime, converter)

fun View.until(path: String, action: () -> Unit): PropertyBinding = oneWayPropertyBinding(path, Action1 { action() }, true, EmptyOneWayConverter())
fun <T> View.until(path: String, converter: OneWayConverter<out T> = EmptyOneWayConverter(), action: (T) -> Unit): PropertyBinding = oneWayPropertyBinding(path, Action1<T> { action(it) }, true, converter)
fun <T> View.until(paths: List<String>, converter: MultipleConverter<out T> , action: (T) -> Unit): PropertyBinding = multiplePropertyBinding(paths, Action1<T> { action(it) }, true, converter)

fun View.enabled(path: String, mode: OneWay = BindingMode.OneWay, oneTime: Boolean = false, converter: OneWayConverter<Boolean> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, enabled(), oneTime, converter)
fun View.enabled(paths: List<String>, oneTime: Boolean = false, converter: MultipleConverter<Boolean>) : PropertyBinding = multiplePropertyBinding(paths, enabled(), oneTime, converter)

fun View.visibility(path: String, mode: OneWay = BindingMode.OneWay, oneTime: Boolean = false, converter: OneWayConverter<Boolean> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, visibility(), oneTime, converter)
fun View.visibility(paths: List<String>, oneTime: Boolean = false, converter: MultipleConverter<Boolean>) : PropertyBinding = multiplePropertyBinding(paths, visibility(), oneTime, converter)

fun View.background(path: String, mode: OneWay = BindingMode.OneWay, oneTime: Boolean = false, converter: OneWayConverter<Drawable> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, background(), oneTime, converter)
fun View.background(paths: List<String>, oneTime: Boolean = false, converter: MultipleConverter<Drawable>) : PropertyBinding = multiplePropertyBinding(paths, background(), oneTime, converter)

fun View.backgroundColor(path: String, mode: OneWay = BindingMode.OneWay, oneTime: Boolean = false, converter: OneWayConverter<Int> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, backgroundColor(), oneTime, converter)
fun View.backgroundColor(paths: List<String>, oneTime: Boolean = false, converter: MultipleConverter<Int>) : PropertyBinding = multiplePropertyBinding(paths, backgroundColor(), oneTime, converter)


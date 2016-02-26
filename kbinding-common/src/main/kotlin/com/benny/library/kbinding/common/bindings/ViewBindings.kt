@file:Suppress("UNUSED_PARAMETER")

package com.benny.library.kbinding.common.bindings

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.converter.EmptyOneWayConverter1
import com.benny.library.kbinding.converter.OneWayConverter
import com.jakewharton.rxbinding.view.RxView
import rx.functions.Action1

/**
 * Created by benny on 12/16/15.
 */


fun Drawable.level(): Action1<Int> = Action1 { level = it }

var View.bg: Drawable
    get() = background
    set(value) {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN ) {
            background = value
        } else {
            setBackgroundDrawable(value)
        }
    }

fun View.background(): Action1<Drawable> = Action1 { bg = it }

fun View.backgroundColor(): Action1<Int> = Action1 { setBackgroundColor(it) }

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

fun View.click(path: String) : PropertyBinding = commandBinding(path, RxView.clicks(this).map { Unit }, RxView.enabled(this))

// Property

fun Drawable.level(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<*, Int> = EmptyOneWayConverter1()) : PropertyBinding = oneWayPropertyBinding(paths, level(), false, converter)
fun Drawable.level(vararg paths: String, mode: OneTime, converter: OneWayConverter<*, Int> = EmptyOneWayConverter1()) : PropertyBinding = oneWayPropertyBinding(paths, level(), true, converter)

fun View.until(vararg paths: String, action: () -> Unit): PropertyBinding = oneWayPropertyBinding(paths, Action1 { action() }, true, EmptyOneWayConverter1())
fun <T> View.until(vararg paths: String, converter: OneWayConverter<*, out T>, action: (T) -> Unit): PropertyBinding = oneWayPropertyBinding(paths, Action1<T> { action(it) }, true, converter)

fun View.enabled(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<*, Boolean> = EmptyOneWayConverter1()) : PropertyBinding = oneWayPropertyBinding(paths, RxView.enabled(this), false, converter)
fun View.enabled(vararg paths: String, mode: OneTime, converter: OneWayConverter<*, Boolean>) : PropertyBinding = oneWayPropertyBinding(paths, RxView.enabled(this), true, converter)

fun View.visibility(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<*, Boolean> = EmptyOneWayConverter1()) : PropertyBinding = oneWayPropertyBinding(paths, RxView.visibility(this), false, converter)
fun View.visibility(vararg paths: String, mode: OneTime, converter: OneWayConverter<*, Boolean> = EmptyOneWayConverter1()) : PropertyBinding = oneWayPropertyBinding(paths, RxView.visibility(this), true, converter)

fun View.background(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<*, Drawable> = EmptyOneWayConverter1()) : PropertyBinding = oneWayPropertyBinding(paths, background(), false, converter)
fun View.background(vararg paths: String, mode: OneTime, converter: OneWayConverter<*, Drawable> = EmptyOneWayConverter1()) : PropertyBinding = oneWayPropertyBinding(paths, background(), true, converter)

fun View.backgroundColor(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<*, Int> = EmptyOneWayConverter1()) : PropertyBinding = oneWayPropertyBinding(paths, backgroundColor(), false, converter)
fun View.backgroundColor(vararg paths: String, mode: OneTime, converter: OneWayConverter<*, Int> = EmptyOneWayConverter1()) : PropertyBinding = oneWayPropertyBinding(paths, backgroundColor(), true, converter)


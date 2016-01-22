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

fun Drawable.level(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Int> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(level(), false, converter, *paths)
fun Drawable.level(vararg paths: String, mode: OneTime, converter: OneWayConverter<Int> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(level(), true, converter, *paths)

fun View.until(path: String, action: () -> Unit): PropertyBinding = oneWayPropertyBinding(path, Action1 { action() }, true, EmptyOneWayConverter())
fun <T> View.until(vararg paths: String, converter: OneWayConverter<out T> = EmptyOneWayConverter(), action: (T) -> Unit): PropertyBinding = oneWayPropertyBinding(Action1<T> { action(it) }, true, converter, *paths)

fun View.enabled(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Boolean>) : PropertyBinding = oneWayPropertyBinding(enabled(), false, converter, *paths)
fun View.enabled(vararg paths: String, mode: OneTime, converter: OneWayConverter<Boolean>) : PropertyBinding = oneWayPropertyBinding(enabled(), true, converter, *paths)

fun View.visibility(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Boolean> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(visibility(), false, converter, *paths)
fun View.visibility(vararg paths: String, mode: OneTime, converter: OneWayConverter<Boolean> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(visibility(), true, converter, *paths)

fun View.background(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Drawable> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(background(), false, converter, *paths)
fun View.background(vararg paths: String, mode: OneTime, converter: OneWayConverter<Drawable> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(background(), true, converter, *paths)

fun View.backgroundColor(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Int> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(backgroundColor(), false, converter)
fun View.backgroundColor(vararg paths: String, mode: OneTime, converter: OneWayConverter<Int> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(backgroundColor(), true, converter)


@file:Suppress("UNUSED_PARAMETER")

package com.benny.library.kbinding.support.v4.bindings

import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.widget.ListAdapter
import android.widget.ListView
import com.benny.library.kbinding.bind.*
import com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout

import com.benny.library.kbinding.common.bindings.swapAdapter
import com.benny.library.kbinding.converter.EmptyOneWayConverter
import com.benny.library.kbinding.converter.OneWayConverter
import rx.functions.Action1

/**
 * Created by benny on 12/16/15.
 */

fun ViewPager.adapter(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<PagerAdapter> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, Action1 { adapter = it }, false, converter)
fun ViewPager.adapter(vararg paths: String, mode: OneTime, converter: OneWayConverter<PagerAdapter> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, Action1 { adapter = it }, true, converter)

fun ViewPager.fragmentAdapter(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<FragmentPagerAdapter> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, Action1 { adapter = it }, false, converter)
fun ViewPager.fragmentAdapter(vararg paths: String, mode: OneTime, converter: OneWayConverter<FragmentPagerAdapter> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, Action1 { adapter = it }, true, converter)
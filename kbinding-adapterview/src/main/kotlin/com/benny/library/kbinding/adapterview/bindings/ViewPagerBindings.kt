@file:Suppress("UNUSED_PARAMETER")

package com.benny.library.kbinding.adapterview.bindings

import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.widget.AdapterView
import com.benny.library.autoadapter.AutoPagerAdapter
import com.benny.library.kbinding.adapterview.bindings.utils.ViewPagerItemClickOnSubscribe
import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.converter.OneWayConverter
import rx.Observable
import rx.functions.Action1

/**
 * Created by benny on 12/16/15.
 */

@Suppress("UNCHECKED_CAST")
fun ViewPager.setOnItemClickListener(onItemClickListener: AdapterView.OnItemClickListener?) {
    tag = onItemClickListener
    if(onItemClickListener != null) (adapter as? AutoPagerAdapter<*>)?.setOnItemClickListener(onItemClickListener)
}

fun ViewPager.itemClicks(): Observable<Int> {
    return Observable.create(ViewPagerItemClickOnSubscribe(this))
}

fun ViewPager.itemClick(path: String) : PropertyBinding = commandBinding(path, itemClicks(), Action1 {})

fun ViewPager.adapter(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<*, PagerAdapter>) : PropertyBinding = oneWayPropertyBinding(paths, Action1 { adapter = it }, false, converter)
fun ViewPager.adapter(vararg paths: String, mode: OneTime, converter: OneWayConverter<*, PagerAdapter>) : PropertyBinding = oneWayPropertyBinding(paths, Action1 { adapter = it }, true, converter)

fun ViewPager.fragmentAdapter(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<*, FragmentPagerAdapter>) : PropertyBinding = oneWayPropertyBinding(paths, Action1 { adapter = it }, false, converter)
fun ViewPager.fragmentAdapter(vararg paths: String, mode: OneTime, converter: OneWayConverter<*, FragmentPagerAdapter>) : PropertyBinding = oneWayPropertyBinding(paths, Action1 { adapter = it }, true, converter)
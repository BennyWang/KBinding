package com.benny.library.kbinding.dsl

import android.widget.RadioGroup
import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.converter.EmptyOneWayConverter
import com.benny.library.kbinding.converter.MultipleConverter
import com.benny.library.kbinding.converter.OneWayConverter
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.widget.checked
import com.jakewharton.rxbinding.widget.checkedChanges

/**
 * Created by benny on 12/28/15.
 */

//Event

fun RadioGroup.checkedChanges(path: String) : PropertyBinding = commandBinding(path, checkedChanges(), enabled())

//Property

fun RadioGroup.checked(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Int> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, checked(), false, converter)
fun RadioGroup.checked(vararg paths: String, mode: OneTime = BindingMode.OneTime, converter: OneWayConverter<Int> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, checked(), true, converter)


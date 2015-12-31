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

fun RadioGroup.checked(path: String, mode: OneWay = BindingMode.OneWay, oneTime: Boolean = false, converter: OneWayConverter<Int> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, checked(), oneTime, converter)
fun RadioGroup.checked(paths: List<String>, oneTime: Boolean = false, converter: MultipleConverter<Int>) : PropertyBinding = multiplePropertyBinding(paths, checked(), oneTime, converter)


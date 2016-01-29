package com.benny.library.kbinding.dsl

import android.widget.RadioGroup
import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.converter.EmptyOneWayConverter
import com.benny.library.kbinding.converter.OneWayConverter
import com.jakewharton.rxbinding.view.RxView
import com.jakewharton.rxbinding.widget.RxRadioGroup

/**
 * Created by benny on 12/28/15.
 */

//Event

fun RadioGroup.checkedChanges(path: String) : PropertyBinding = commandBinding(path, RxRadioGroup.checkedChanges(this), RxView.enabled(this))

//Property

fun RadioGroup.checked(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Int> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, RxRadioGroup.checked(this), false, converter)
fun RadioGroup.checked(vararg paths: String, mode: OneTime = BindingMode.OneTime, converter: OneWayConverter<Int> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, RxRadioGroup.checked(this), true, converter)


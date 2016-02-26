@file:Suppress("UNUSED_PARAMETER")

package com.benny.library.kbinding.common.bindings

import android.net.Uri
import android.widget.ImageView
import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.converter.EmptyOneWayConverter1
import com.benny.library.kbinding.converter.OneWayConverter
import rx.functions.Action1

/**
 * Created by benny on 12/16/15.
 */

//For ImageView

fun ImageView.src(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<*, String> = EmptyOneWayConverter1()) : PropertyBinding = oneWayPropertyBinding(paths, Action1 { setImageURI(Uri.parse(it)) }, false, converter)
fun ImageView.src(vararg paths: String, mode: OneTime, converter: OneWayConverter<*, String> = EmptyOneWayConverter1()) : PropertyBinding = oneWayPropertyBinding(paths, Action1 { setImageURI(Uri.parse(it)) }, true, converter)




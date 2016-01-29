package com.benny.library.kbinding.dsl

import android.net.Uri
import android.widget.ImageView
import com.benny.library.kbinding.bind.*
import com.benny.library.kbinding.converter.*
import rx.functions.Action1

/**
 * Created by benny on 12/16/15.
 */

//For ImageView

fun ImageView.src(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<String> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, Action1 { setImageURI(Uri.parse(it)) }, false, converter)
fun ImageView.src(vararg paths: String, mode: OneTime, converter: OneWayConverter<String> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, Action1 { setImageURI(Uri.parse(it)) }, true, converter)




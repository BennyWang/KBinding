package com.benny.library.kbinding.common

import android.view.View
import android.view.ViewManager
import org.jetbrains.anko.AnkoException

/**
 * Created by benny on 12/30/15.
 */
class Style<T : View>(val style: T.() -> Unit) {
    fun perform(view: T) {
        view.style()
    }
}

fun <T : View> ViewManager.viewStyle(init: T.() -> Unit) : Style<T> {
    return Style(init)
}

var <T: View> T.style: Style<in T>
    set(value) = value.perform(this)
    get() = throw AnkoException("'style' property does not have a getter")
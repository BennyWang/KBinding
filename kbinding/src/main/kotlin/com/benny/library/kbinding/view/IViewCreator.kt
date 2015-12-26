package com.benny.library.kbinding.view

import android.view.View
import android.view.ViewGroup
import com.benny.library.kbinding.bind.BindingDisposer

/**
 * Created by benny on 11/19/15.
 */
interface IViewCreator<T> {
    fun viewTypeFor(data: T?, position: Int): Int
    fun viewTypeCount(): Int
    fun view(container: ViewGroup): View
}
package com.benny.library.neobinding.kotlin.view

import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 * Created by benny on 11/19/15.
 */
interface IViewCreator<T> {
    fun viewTypeFor(data: T?, position: Int): Int
    fun viewTypeCount(): Int
    fun view(container: ViewGroup): View
}
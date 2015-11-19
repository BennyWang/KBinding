package com.benny.library.neobinding.kotlin.bind

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by benny on 11/18/15.
 */

public open class ViewCreator<T> : IViewCreator<T> {
    val layoutId:Int
    val viewModelBinder: ViewModelBinder<T>

    constructor(viewModelBinder: ViewModelBinder<T>, layoutId: Int) {
        this.viewModelBinder = viewModelBinder
        this.layoutId = layoutId
    }

    override fun view(context: Context, container: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(layoutId, container, false)
        view.tag = viewModelBinder.bind(view)
        return view
    }

    override fun viewTypeFor(data: T?, position: Int): Int {
        return  0
    }

    override fun viewTypeCount(): Int {
        return 1
    }
}
package com.benny.library.neobinding.kotlin.bind

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by benny on 11/18/15.
 */

public open class ViewCreator<T> {
    val layoutId:Int
    val viewModelBinder: ViewModelBinder<T>

    constructor(viewModelBinder: ViewModelBinder<T>, layoutId: Int) {
        this.viewModelBinder = viewModelBinder
        this.layoutId = layoutId
    }

    open fun view(container: ViewGroup): View {
        val view = LayoutInflater.from(container.context).inflate(layoutId, container, false)
        view.tag = viewModelBinder.bind(view)
        return view
    }
}
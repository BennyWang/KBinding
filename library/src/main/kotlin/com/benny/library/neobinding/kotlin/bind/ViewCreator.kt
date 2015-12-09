package com.benny.library.neobinding.kotlin.bind

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benny.library.neobinding.kotlin.view.ViewComponent

/**
 * Created by benny on 11/18/15.
 */

public open class ViewCreator<T> : IViewCreator<T> {
    val viewComponent: ViewComponent
    val viewBinder: ViewBinder<BindableModel<in T> >

    constructor(viewBinder: ViewBinder<BindableModel<in T> >, viewComponent: ViewComponent) {
        this.viewBinder = viewBinder
        this.viewComponent = viewComponent
    }

    override fun view(context: Context, container: ViewGroup?): View {
        val view = viewComponent.createView(context)
        view.tag = viewBinder.bind(view)
        return view
    }

    override fun viewTypeFor(data: T?, position: Int): Int {
        return  0
    }

    override fun viewTypeCount(): Int {
        return 1
    }
}
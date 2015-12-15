package com.benny.library.neobinding.view

import android.app.Activity
import android.content.Context
import android.view.View
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

/**
 * Created by benny on 12/9/15.
 */

interface ViewBinderComponent<T> : AnkoComponent<T> {
    fun createViewBinder(context: Context): ViewBinder

    override fun createView(ui: AnkoContext<T>): View {
        return createViewBinder(ui.ctx).contentView
    }

    fun setContentView(activity: Activity) : ViewBinder {
        return createViewBinder(activity).apply { activity.setContentView(contentView) }
    }
}
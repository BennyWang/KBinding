package com.benny.library.neobinding.view

import android.app.Activity
import android.content.Context
import android.view.View
import com.benny.library.neobinding.extension.bindableLayout
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

/**
 * Created by benny on 12/9/15.
 */

interface ViewBinderComponent<T> : AnkoComponent<T>, ViewComponent {
    fun createViewBinder(context: Context): ViewBinder {
        val viewBuilder = builder()
        return context.bindableLayout { this@bindableLayout.viewBuilder() }
    }

    //just for preview
    override fun createView(ui: AnkoContext<T>): View = with(ui) {
        val viewBuilder = builder()
        this.viewBuilder()
        return view
    }

    fun setContentView(activity: Activity) : ViewBinder {
        return createViewBinder(activity).apply { activity.setContentView(view) }
    }
}
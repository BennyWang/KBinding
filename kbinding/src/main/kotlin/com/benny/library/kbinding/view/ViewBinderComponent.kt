package com.benny.library.kbinding.view

import android.app.Activity
import android.content.Context
import android.view.View
import com.benny.library.kbinding.dsl.bindableLayout
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoContextImpl

/**
 * Created by benny on 12/9/15.
 */

interface ViewBinderComponent<T> : AnkoComponent<T>, ViewComponent<T> {
    fun createViewBinder(ankoContext: AnkoContext<T>): ViewBinder {
        val viewBuilder = builder()
        return ankoContext.bindableLayout { this@bindableLayout.viewBuilder() }
    }

    fun createViewBinder(ctx: Context, owner: T): ViewBinder {
        val viewBuilder = builder()
        return AnkoContext.create(ctx, owner).bindableLayout { this@bindableLayout.viewBuilder() }
    }

    //just for preview
    override fun createView(ui: AnkoContext<T>): View = with(ui) {
        val viewBuilder = builder()
        this.viewBuilder()
        return view
    }
}

fun <T : Activity> ViewBinderComponent<T>.setContentView(activity: T) = createViewBinder(AnkoContextImpl(activity, activity, true))

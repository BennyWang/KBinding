package com.benny.library.kbinding.view

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import com.benny.library.kbinding.dsl.bindableLayout
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoContextImpl

/**
 * Created by benny on 12/9/15.
 */

interface ItemViewBinderComponent : ViewBinderComponent<ViewGroup> {
    override fun createViewBinder(ankoContext: AnkoContext<ViewGroup>): ViewBinder {
        return super.createViewBinder(ankoContext)
    }
}


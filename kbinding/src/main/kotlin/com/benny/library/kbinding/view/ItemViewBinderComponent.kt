package com.benny.library.kbinding.view

import android.view.ViewGroup
import org.jetbrains.anko.AnkoContext

/**
 * Created by benny on 12/9/15.
 */

interface ItemViewBinderComponent : ViewBinderComponent<ViewGroup> {
    override fun createViewBinder(ankoContext: AnkoContext<ViewGroup>): ViewBinder {
        return super.createViewBinder(ankoContext)
    }
}


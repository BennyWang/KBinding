package com.benny.library.kbinding.adapterview.viewcreator

import android.view.ViewGroup
import com.benny.library.kbinding.view.ViewBinder
import com.benny.library.kbinding.view.ViewBinderComponent
import org.jetbrains.anko.AnkoContext

/**
 * Created by benny on 12/9/15.
 */

interface ItemViewBinderComponent : ViewBinderComponent<ViewGroup> {
    override fun createViewBinder(ankoContext: AnkoContext<ViewGroup>): ViewBinder {
        return super.createViewBinder(ankoContext)
    }
}


package com.benny.library.neobinding.view

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewManager
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

/**
 * Created by benny on 12/9/15.
 */

interface ViewComponent {
    fun builder(): AnkoContext<*>.() -> Unit
}
package com.benny.library.neobinding.kotlin.view

import android.content.Context
import android.view.View

/**
 * Created by benny on 12/9/15.
 */

interface ViewComponent {
    fun create(context: Context): ViewBinder
}
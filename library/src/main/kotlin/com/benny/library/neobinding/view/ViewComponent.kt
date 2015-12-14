package com.benny.library.neobinding.view

import android.content.Context

/**
 * Created by benny on 12/9/15.
 */

interface ViewComponent {
    fun create(context: Context): ViewBinder
}
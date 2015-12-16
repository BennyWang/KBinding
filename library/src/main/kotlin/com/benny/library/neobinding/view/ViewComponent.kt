package com.benny.library.neobinding.view

import org.jetbrains.anko.AnkoContext

/**
 * Created by benny on 12/9/15.
 */

interface ViewComponent {
    fun builder(): AnkoContext<*>.() -> Unit
}
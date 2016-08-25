package com.benny.library.kbinding.view

import org.jetbrains.anko.AnkoContext

/**
 * Created by benny on 12/9/15.
 */

interface ViewComponent<in T> {
    fun builder(): AnkoContext<out T>.() -> Unit
}
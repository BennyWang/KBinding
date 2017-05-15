package com.benny.library.kbinding.view

import org.jetbrains.anko.AnkoContext

interface ViewComponent<in T> {
    fun builder(): AnkoContext<T>.() -> Unit
}
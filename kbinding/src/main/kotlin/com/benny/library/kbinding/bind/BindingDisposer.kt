package com.benny.library.kbinding.bind

import java.util.*

/**
 * Created by benny on 11/17/15.
 */

class BindingDisposer {
    private val unbinds: MutableList<() -> Unit> = ArrayList()

    fun unbind() {
        unbinds.forEach { it() }
        unbinds.clear()
    }

    fun add(unbind: () -> Unit) {
        unbinds.add(unbind)
    }
}
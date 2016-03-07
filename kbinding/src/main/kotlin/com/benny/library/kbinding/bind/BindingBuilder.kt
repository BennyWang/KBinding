package com.benny.library.kbinding.bind

/**
 * Created by benny on 3/3/16.
 */

interface BindingBuilder<T> {
    fun build(target: T)
}

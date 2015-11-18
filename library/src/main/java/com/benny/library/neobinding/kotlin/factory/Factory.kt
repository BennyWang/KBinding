package com.benny.library.neobinding.kotlin.factory

/**
 * Created by benny on 11/18/15.
 */

public interface Factory<T> {
    fun create(): T
}
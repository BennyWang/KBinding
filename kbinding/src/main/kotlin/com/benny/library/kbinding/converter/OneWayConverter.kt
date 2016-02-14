@file:Suppress("UNCHECKED_CAST")

package com.benny.library.kbinding.converter

/**
 * Created by benny on 11/18/15.
 */

interface OneWayConverter<T> {
    fun convert(source: Any?): T
}

class EmptyOneWayConverter<T> : OneWayConverter<T> {
    override fun convert(source: Any?): T {
        return source as T
    }
}

class StringConverter : OneWayConverter<String> {
    override fun convert(source: Any?): String {
        return source.toString()
    }
}

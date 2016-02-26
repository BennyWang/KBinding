@file:Suppress("UNCHECKED_CAST")

package com.benny.library.kbinding.converter

/**
 * Created by benny on 11/18/15.
 */

/*interface OneWayConverter<T> {
    fun convert(source: Any?): T
}*/

class EmptyOneWayConverter1<T> : OneWayConverter<Any?, T> {
    override fun convert(source: Any?): T {
        return source as T
    }
}

class EmptyOneWayConverter2<T, R> : OneWayConverter<T, R> {
    override fun convert(source: T): R {
        return source as R
    }
}

class StringConverter : OneWayConverter<Any?, String> {
    override fun convert(source: Any?): String {
        return source.toString()
    }
}

package com.benny.library.kbinding.converter

import android.util.Log

/**
 * Created by benny on 11/18/15.
 */

public interface MultipleConverter<T> : OneWayConverter<T> {
    fun convert(params: Array<Any>): T

    override fun convert(source: Any?): T {
        throw UnsupportedOperationException()
    }
}

class ArrayToBooleanConverter : MultipleConverter<Boolean> {
    override fun convert(params: Array<Any>): Boolean {
        params.forEach {
            if(it.toString().isEmpty()) return false
        }
        return true
    }
}
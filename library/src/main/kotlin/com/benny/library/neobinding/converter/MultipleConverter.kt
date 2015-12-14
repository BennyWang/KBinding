package com.benny.library.neobinding.converter

import android.util.Log

/**
 * Created by benny on 11/18/15.
 */

public interface MultipleConverter<T> {
    fun convert(params: Array<Any>): T
}

class ArrayToBooleanConverter : MultipleConverter<Boolean> {
    override fun convert(params: Array<Any>): Boolean {
        params.forEach {
            if(it.toString().isEmpty()) return false
        }
        return true
    }
}
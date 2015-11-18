package com.benny.library.neobinding.kotlin.converter

/**
 * Created by benny on 11/18/15.
 */

public interface MultipleConverter<T> {
    fun convert(vararg `var`: Any): T
}

class ArrayToBoolenConverter : MultipleConverter<Boolean> {
    override fun convert(vararg `var`: Any): Boolean {
        for (source in `var`) {
            if (source.toString().isEmpty()) return false
        }
        return true
    }
}
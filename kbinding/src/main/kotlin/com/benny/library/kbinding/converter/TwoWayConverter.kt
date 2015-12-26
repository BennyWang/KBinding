package com.benny.library.kbinding.converter

/**
 * Created by benny on 11/18/15.
 */

public interface TwoWayConverter<T, R> {
    fun convert(source: Any): R
    fun convertBack(source: Any): T
}

public class EmptyTwoWayConverter<T, R> : TwoWayConverter<T, R> {
    override fun convert(source: Any): R {
        return source as R
    }

    override fun convertBack(source: Any): T {
        return source as T
    }
}
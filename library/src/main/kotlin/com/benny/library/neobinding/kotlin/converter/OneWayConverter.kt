package com.benny.library.neobinding.kotlin.converter

/**
 * Created by benny on 11/18/15.
 */

public interface OneWayConverter<T> {
    fun convert(source: Any): T
}

public class EmptyOneWayConverter<T> : OneWayConverter<T> {
    override fun convert(source: Any): T {
        return source as T
    }
}
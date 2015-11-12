package com.benny.library.neobinding.converter;

/**
 * Created by benny on 11/6/15.
 */
public interface TwoWayPropertyConverter<T, R> {
    T convert(Object source);
    R convertBack(Object source);
}

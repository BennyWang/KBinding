package com.benny.library.neobinding.converter;

/**
 * Created by benny on 11/6/15.
 */
public interface TwoWayPropertyConverter<T, R> {
    R convert(Object source);
    T convertBack(Object source);
}

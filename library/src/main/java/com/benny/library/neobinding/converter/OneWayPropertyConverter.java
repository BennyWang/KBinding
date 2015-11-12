package com.benny.library.neobinding.converter;

/**
 * Created by benny on 11/6/15.
 */
public interface OneWayPropertyConverter<T> {
    T convert(Object source);
}

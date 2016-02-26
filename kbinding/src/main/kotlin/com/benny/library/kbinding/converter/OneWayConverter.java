package com.benny.library.kbinding.converter;

/**
 * Created by benny on 2/26/16.
 */
public interface OneWayConverter<T, R> {
    R convert(T input);
}

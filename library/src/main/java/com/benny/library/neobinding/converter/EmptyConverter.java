package com.benny.library.neobinding.converter;

/**
 * Created by benny on 11/6/15.
 */
public class EmptyConverter<T> implements OneWayPropertyConverter<T> {
    public T convert(Object source) {
        return (T)source;
    }
}

package com.benny.library.neobinding.converter;

/**
 * Created by benny on 11/6/15.
 */
public class EmptyTwoWayConverter<T, R> implements TwoWayPropertyConverter<T, R> {
    public T convert(Object source) {
        return (T)source;
    }

    @Override
    public R convertBack(Object source) {
        return (R)source;
    }
}

package com.benny.library.neobinding.converter;

/**
 * Created by benny on 11/6/15.
 */
public class EmptyTwoWayConverter<T, R> implements TwoWayPropertyConverter<T, R> {
    public R convert(Object source) {
        return (R)source;
    }

    @Override
    public T convertBack(Object source) {
        return (T)source;
    }
}

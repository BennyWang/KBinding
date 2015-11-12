package com.benny.library.neobinding.converter;


/**
 * Created by benny on 11/6/15.
 */

public interface MultiplePropertyConverter<T> {
    T convert(Object... var);
}

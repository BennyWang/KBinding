package com.benny.library.neobinding.converter;

/**
 * Created by benny on 11/8/15.
 */
public class ArrayToBoolenConverter implements MultiplePropertyConverter<Boolean> {
    @Override
    public Boolean convert(Object... var) {
        for (Object source : var) {
            if(source.toString().isEmpty()) return false;
        }
        return true;
    }
}

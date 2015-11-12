package com.benny.library.neobinding.converter;

import android.view.View;

/**
 * Created by benny on 11/8/15.
 */
public class StringToVisibilityConverter implements OneWayPropertyConverter<Integer> {
    private int notVisible = View.GONE;

    public StringToVisibilityConverter() {
    }

    public StringToVisibilityConverter(int notVisible) {
        this.notVisible = notVisible;
    }

    @Override
    public Integer convert(Object source) {
        String string = (String)source;
        return (string != null && !string.isEmpty()) ? View.VISIBLE : notVisible;
    }
}

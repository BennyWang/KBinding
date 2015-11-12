package com.benny.library.neobinding.converter;

import android.graphics.Color;

/**
 * Created by benny on 11/8/15.
 */
public class NumberToColorConverter implements OneWayPropertyConverter<Integer> {
    private static int GREEN = Color.parseColor("#0f9920");
    private static int RED = Color.parseColor("#e74524");

    private int zeroColor = Color.BLACK;

    public static void setColor(int red, int green) {
        RED = red;
        GREEN = green;
    }

    public NumberToColorConverter(int zeroColor) {
        this.zeroColor = zeroColor;
    }

    @Override
    public Integer convert(Object source) {
        double data = ((Number)source).doubleValue();
        return data == 0 ? zeroColor : (data > 0 ? RED : GREEN);
    }
}

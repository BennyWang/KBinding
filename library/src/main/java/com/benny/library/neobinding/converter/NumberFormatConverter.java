package com.benny.library.neobinding.converter;

import java.text.DecimalFormat;

/**
 * Created by benny on 11/8/15.
 */
public class NumberFormatConverter {
    private static DecimalFormat twoAfterPoint = new DecimalFormat("0.00");
    private static DecimalFormat twoAfterPointWithSigned = new DecimalFormat("+0.00;-0.00");

    public static class StockPriceFormat implements OneWayPropertyConverter<String> {
        @Override
        public String convert(Object source) {
            return twoAfterPoint.format(((Number)source).doubleValue());
        }
    }

    public static class StockPricePercentFormat implements OneWayPropertyConverter<String> {
        @Override
        public String convert(Object source) {
            return twoAfterPointWithSigned.format(((Number)source).doubleValue());
        }
    }
}

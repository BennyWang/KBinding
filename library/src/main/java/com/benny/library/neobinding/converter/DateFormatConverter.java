package com.benny.library.neobinding.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by benny on 11/8/15.
 */
public class DateFormatConverter {

    public static class SimpleFormat implements OneWayPropertyConverter<String> {
        private String format = "yyyy-M-dd HH:mm:ss";

        public SimpleFormat() {}

        public SimpleFormat(String format) {
            this.format = format;
        }

        @Override
        public String convert(Object source) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format((Date)source);
        }
    }
}

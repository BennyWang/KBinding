package com.benny.library.neobinding.converter;

import android.net.Uri;

/**
 * Created by benny on 11/6/15.
 */
public class StringToUriConverter implements OneWayPropertyConverter<Uri> {
    public Uri convert(Object source) {
        return Uri.parse((String)source);
    }
}

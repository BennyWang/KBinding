package com.benny.app.sample.extension

import android.content.Context
import android.widget.Toast

/**
 * Created by benny on 11/30/15.
 */

val SHORT_DURATION: Int = 3000
val LONG_DURATION: Int = 5000

fun String?.toast(context: Context) {
    if(this == null) return
    Toast.makeText(context, this, SHORT_DURATION).show();
}

fun String?.longToast(context: Context) {
    if(this == null) return
    Toast.makeText(context, this, LONG_DURATION).show();
}

package com.benny.library.neobinding.kotlin.view

import android.app.Activity
import android.view.View

/**
 * Created by benny on 12/10/15.
 */
interface  ViewFinder {
    fun <T: View> find(viewId: Int): T
}


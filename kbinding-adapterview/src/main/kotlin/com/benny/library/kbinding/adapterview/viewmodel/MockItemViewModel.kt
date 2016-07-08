package com.benny.library.kbinding.adapterview.viewmodel;

import com.benny.library.autoadapter.viewholder.DataGetter

/**
 * Created by benny on 11/17/15.
 */

class MockItemViewModel<T> : ItemViewModel<T>() {
    override fun onDataChange(getter: DataGetter<T>?, position: Int) {
    }
}
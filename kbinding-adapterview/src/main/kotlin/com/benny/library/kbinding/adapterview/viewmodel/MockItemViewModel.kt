package com.benny.library.kbinding.adapterview.viewmodel;

import com.benny.library.autoadapter.listener.DataChangeListener
import com.benny.library.kbinding.viewmodel.ViewModel
import kotlin.properties.Delegates

/**
 * Created by benny on 11/17/15.
 */

class MockItemViewModel<T> : ItemViewModel<T>() {
    override fun onDataChange(data: T) {
    }
}
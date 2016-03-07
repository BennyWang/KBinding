package com.benny.library.kbinding.viewmodel;

import com.benny.library.kbinding.viewmodel.ViewModel
import kotlin.properties.Delegates

/**
 * Created by benny on 11/17/15.
 */

abstract class ItemViewModel<T> : ViewModel() {

    var position: Int by Delegates.property(0)

    init {
        bindPropertyV2("position", 0)
    }

    fun notifyPropertyChange(t: T?, position: Int) {
        this.position = position
        updateData(t)
    }

    abstract fun updateData(t: T?)
}
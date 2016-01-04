package com.benny.library.kbinding.bind;

import android.util.Log

/**
 * Created by benny on 11/17/15.
 */
public abstract class ItemViewModel<T> : ViewModel() {

    public var position: Int by bindProperty("position", 0)

    public fun notifyPropertyChange(t: T?, position: Int) {
        this.position = position
        updateData(t)
    }

    abstract fun updateData(t: T?)
}

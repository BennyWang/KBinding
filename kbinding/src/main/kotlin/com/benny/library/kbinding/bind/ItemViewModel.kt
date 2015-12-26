package com.benny.library.kbinding.bind;

import kotlin.properties.Delegates


/**
 * Created by benny on 11/17/15.
 */
public abstract class ItemViewModel<T> : ViewModel() {

    public var position: Int by Delegates.bindProperty("position", 0)

    public fun notifyPropertyChange(t: T?, position: Int) {
        this.position = position
        updateData(t)
    }

    abstract fun updateData(t: T?)
}

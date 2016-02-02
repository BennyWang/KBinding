package com.benny.library.kbinding.bind;

/**
 * Created by benny on 11/17/15.
 */
abstract class ItemViewModel<T> : ViewModel() {

    var position: Int by bindProperty("position") { 0 }

    fun notifyPropertyChange(t: T?, position: Int) {
        this.position = position
        updateData(t)
    }

    abstract fun updateData(t: T?)
}
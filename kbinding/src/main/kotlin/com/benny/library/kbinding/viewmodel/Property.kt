package com.benny.library.kbinding.viewmodel

import rx.Observable
import rx.Observer
import rx.subjects.BehaviorSubject

/**
 * Created by benny on 11/17/15.
 */

class Property<T>(val defaultValue:T? = null) {
    private var property: BehaviorSubject<T> = if(defaultValue == null) BehaviorSubject.create() else BehaviorSubject.create(defaultValue)

    var value: T?
        get() = property.value
        set(value) { property.onNext(value) }

    val observable: Observable<T>
        get() {
            ensurePropertyInitialized()
            return property
        }

    val observer: Observer<T>
        get() {
            ensurePropertyInitialized()
            return property
        }

    fun ensurePropertyInitialized() {
        if(property.hasCompleted()) property = if(defaultValue == null) BehaviorSubject.create() else BehaviorSubject.create(defaultValue)
    }
}
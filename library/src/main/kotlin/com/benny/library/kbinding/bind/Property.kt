package com.benny.library.kbinding.bind

import rx.Observable
import rx.Observer
import rx.subjects.BehaviorSubject

/**
 * Created by benny on 11/17/15.
 */

public class Property<T>(val defaultValue:T? = null) {
    private var property: BehaviorSubject<T> = if(defaultValue == null) BehaviorSubject.create() else BehaviorSubject.create(defaultValue)

    public var value: T?
        get() = property.value
        set(value) = property.onNext(value)

    public val observable: Observable<T>
        get() {
            ensurePropertyInitialized()
            return property
        }

    public val observer: Observer<T>
        get() {
            ensurePropertyInitialized()
            return property
        }

    fun ensurePropertyInitialized() {
        if(property.hasCompleted()) property = if(defaultValue == null) BehaviorSubject.create() else BehaviorSubject.create(defaultValue)
    }
}
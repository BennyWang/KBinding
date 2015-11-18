package com.benny.library.neobinding.kotlin.bind

import rx.Observable
import rx.Observer
import rx.subjects.BehaviorSubject

/**
 * Created by benny on 11/17/15.
 */

public class Property<T>(defaultValue:T? = null) {
    private val property: BehaviorSubject<T>

    public var value: T
        get() = property.value
        set(value) = property.onNext(value)

    public val observable: Observable<T>
        get() = property

    public val observer: Observer<T>
        get() = property

    public val hasCompleted: Boolean
        get() = property.hasCompleted()

    init {
        property = if(defaultValue == null) BehaviorSubject.create() else BehaviorSubject.create(defaultValue)
    }
}
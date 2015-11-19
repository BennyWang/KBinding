package com.benny.library.neobinding.kotlin.bind

import rx.Observable
import rx.Observer
import rx.subjects.BehaviorSubject

/**
 * Created by benny on 11/17/15.
 */

public class Command<T>(val observable: Observable<T>) {
    val _canExecute: BehaviorSubject<Boolean> = BehaviorSubject.create()
        get() {
            if(field.hasCompleted()) field = BehaviorSubject.create()
            return field
        }

    public val canExecute: Observable<Boolean>
        get() = _canExecute

    public val execute : Observable<T>
        get() = observable.doOnSubscribe{ _canExecute.onNext(false) }.doOnTerminate{ _canExecute.onNext(true) }

}
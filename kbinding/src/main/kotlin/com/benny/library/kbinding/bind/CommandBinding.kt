package com.benny.library.kbinding.bind

import rx.Observable
import rx.Subscription
import rx.functions.Action1

/**
 * Created by benny on 11/17/15.
 */

public class CommandBinding<T>(key: String, val trigger: Observable<T>, val canExecute: Action1<in Boolean> = Action1 {}) : PropertyBinding() {
    public var key: String = key
    private set

    public fun prefix(prefix: String): CommandBinding<T> {
        if(!prefix.isEmpty()) key = "$prefix.$key"
        return this
    }

    fun bindTo(command: Command): Subscription {
        return trigger.subscribe { it -> command.execute(it, canExecute) }
    }
}

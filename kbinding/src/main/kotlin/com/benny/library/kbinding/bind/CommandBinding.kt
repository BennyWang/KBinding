package com.benny.library.kbinding.bind

import com.benny.library.kbinding.viewmodel.Command
import rx.Observable
import rx.Subscription
import rx.functions.Action1

/**
 * Created by benny on 11/17/15.
 */

class CommandBinding<T>(key: String, val trigger: Observable<T>, val canExecute: Action1<in Boolean> = Action1 {}) : PropertyBinding() {
    var key: String = key
    private set

    fun prefix(prefix: String): CommandBinding<T> {
        if(!prefix.isEmpty()) key = "$prefix.$key"
        return this
    }

    fun bindTo(command: Command<T>): Subscription {
        return trigger
                .doOnSubscribe { LogBind(key, "Command") }
                .doOnUnsubscribe { LogUnbind(key, "Command") }
                .subscribe { it -> command.execute(it, canExecute) }
    }
}

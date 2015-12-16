package com.benny.library.neobinding.bind

import rx.Observable
import rx.functions.Action1

/**
 * Created by benny on 11/17/15.
 */

public class CommandBinding(key: String, val trigger: Observable<Unit>, val canExecute: Action1<in Boolean> = Action1 {}) : PropertyBinding() {
    public var key: String = key
    private set

    public fun prefix(prefix: String): CommandBinding {
        if(!prefix.isEmpty()) key = "$prefix.$key"
        return this
    }

    fun bindTo(bindingContext: BindingContext, command: Command) {
        trigger.compose(bindingContext.applyLifecycle<Unit>())
                .subscribe { command.execute(canExecute) }
    }
}

package com.benny.library.neobinding.kotlin.bind

import com.benny.library.neobinding.bind.ViewModelBinder
import rx.Observable
import rx.functions.Action1
import rx.functions.Func1

/**
 * Created by benny on 11/17/15.
 */

public class CommandBinding(val key: String, val trigger: Observable<Unit>, val canExecute: Action1<in Boolean> = Action1 {}) {

    fun bindTo(bindingContext: BindingContext<*>, command: Command<*>) {
        trigger.compose(bindingContext.applyLifecycle<Unit>())
                .doOnSubscribe({ ViewModelBinder.LogBinding("$key execute") })
                .doOnTerminate({ ViewModelBinder.LogUnbinding("$key execute") })
                .subscribe(Action1<kotlin.Any> { command.execute { canExecute.call(it) } })
    }
}

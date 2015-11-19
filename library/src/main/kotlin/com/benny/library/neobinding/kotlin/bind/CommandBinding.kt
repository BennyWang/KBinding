package com.benny.library.neobinding.kotlin.bind

import com.benny.library.neobinding.bind.ViewModelBinder
import rx.Observable
import rx.functions.Action1
import rx.functions.Func1

/**
 * Created by benny on 11/17/15.
 */

public class CommandBinding<T>(val key: String, val trigger: Observable<Any>, val handler: CommandHandler<Throwable, T>, val canExecute: Action1<in Boolean> = Action1 {}) {

    fun bindTo(bindingContext: BindingContext<*>, command: Command<T>) {
        command.canExecute.compose(bindingContext.applyLifecycle<Boolean>())
                .doOnSubscribe({ ViewModelBinder.LogBinding("$key can execute") })
                .doOnTerminate({ ViewModelBinder.LogUnbinding("$key can execute") })
                .subscribe(canExecute)
        trigger.flatMap({ v:Any -> wrapError(command.execute) }).compose(bindingContext.applyLifecycle<T>())
                .doOnSubscribe({ ViewModelBinder.LogBinding("$key execute") })
                .doOnTerminate({ ViewModelBinder.LogUnbinding("$key execute") })
                .subscribe({ handler.onSuccess(it) })
    }

    private fun <T> wrapError(execute: Observable<T>): Observable<T> {
        return execute.onErrorResumeNext({
            throwable: Throwable->handler.onError(throwable)
            Observable.empty<T>()
        })
    }
}

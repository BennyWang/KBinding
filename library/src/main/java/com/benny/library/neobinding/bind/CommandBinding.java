package com.benny.library.neobinding.bind;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by benny on 11/5/15.
 */
public class CommandBinding<T> {
    private String property;
    private Observable<?> trigger;
    private Action1<? super Boolean> canExecute = aBoolean -> {};
    private CommandHandler<Throwable, T> commandHandler;

    public CommandBinding(String property, Observable<?> trigger, CommandHandler<Throwable, T> commandHandler) {
        this.property = property;
        this.trigger = trigger;
        this.commandHandler = commandHandler;
    }

    public CommandBinding canExecute(Action1<? super Boolean> canExecute) {
        this.canExecute = canExecute;
        return this;
    }

    public String property() {
        return property;
    }

    public void bindTo(BindingContext<?> bindingContext, Command<T> command) {
        command.canExecute().compose(bindingContext.applyLifecycle())
                .doOnSubscribe(()->ViewModelBinder.LogBinding(property + " " + "can execute"))
                .doOnTerminate(()->ViewModelBinder.LogUnbinding(property + " " + "can execute"))
                .subscribe(canExecute);
        trigger.flatMap(v -> wrapError(command.execute())).compose(bindingContext.applyLifecycle())
                .doOnSubscribe(() -> ViewModelBinder.LogBinding(property + " " + "execute"))
                .doOnTerminate(() -> ViewModelBinder.LogUnbinding(property + " " + "execute"))
                .subscribe(commandHandler::onSuccess);
    }

    @SuppressWarnings("unchecked")
    Observable<T> wrapError(Observable execute) {
        return execute.onErrorResumeNext((Func1<Throwable, Observable>) throwable -> {
            commandHandler.onError(throwable);
            return Observable.empty();
        });
    }
}

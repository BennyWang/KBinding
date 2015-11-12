package com.benny.library.neobinding.bind;

import rx.functions.Action1;

/**
 * Created by benny on 9/13/15.
 */
public class CommandHandler<T1, T2> {
    private Action1<T1> onError;
    private Action1<T2> onSuccess;


    public static <T1, T2> CommandHandler<T1, T2> create(Action1<T1> onError, Action1<T2> onSuccess) {
        return new CommandHandler<>(onError, onSuccess);
    }

    public static <T1, T2> CommandHandler<T1, T2> create(Action1<T2> onSuccess) {
        return new CommandHandler<>(a->{}, onSuccess);
    }

    private CommandHandler(Action1<T1> onError, Action1<T2> onSuccess) {
        this.onSuccess = onSuccess;
        this.onError = onError;
    }

    public void onError(T1 error) {
        onError.call(error);
    }

    public void onSuccess(T2 data) {
        onSuccess.call(data);
    }
}
package com.benny.library.kbinding.viewmodel;

import rx.functions.Action1;

/**
 * Created by benny on 12/16/15.
 */

public interface Command<T> {
     void execute(T param, Action1<? super Boolean> canExecute);
}

package com.benny.library.neobinding.bind;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;


/**
 * Created by benny on 9/8/15.
 */
public class Command<T> {
    private final Observable<T> observable;
    private BehaviorSubject<Boolean> canExecute;

    public Command(Observable<T> observable) {
        this.observable = observable;
    }

    public Observable<T> execute() {
        ensureSubjectValid();
        return observable
                .doOnSubscribe(() -> canExecute.onNext(false))
                .doOnTerminate(() -> canExecute.onNext(true));
    }

    public Observable<Boolean> canExecute() {
        ensureSubjectValid();
        return canExecute;
    }

    private void ensureSubjectValid() {
        if(canExecute == null || canExecute.hasCompleted()) canExecute = BehaviorSubject.create();
    }
}
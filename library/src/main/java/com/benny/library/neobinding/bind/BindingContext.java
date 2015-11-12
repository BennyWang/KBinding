package com.benny.library.neobinding.bind;

import android.content.Context;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.RxLifecycle;
import rx.Observable;

/**
 * Created by benny on 11/7/15.
 */


public class BindingContext<T> {
    Context context;
    Observable<T> lifecycle;
    T event;

    public BindingContext(Context context, Observable<T> fragmentLifecycle, T event) {
        this.context = context;
        this.event = event;
        this.lifecycle = fragmentLifecycle;
    }

    public <R> Observable.Transformer<R, R> applyLifecycle() {
        return (event instanceof FragmentEvent) ? RxLifecycle.bindUntilFragmentEvent((Observable<FragmentEvent>) lifecycle, (FragmentEvent)event) :
                RxLifecycle.bindUntilActivityEvent((Observable<ActivityEvent>)lifecycle, (ActivityEvent)event);
    }

    public Context getContext() {
        return context;
    }
}

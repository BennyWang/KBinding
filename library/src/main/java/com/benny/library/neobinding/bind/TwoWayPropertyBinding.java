package com.benny.library.neobinding.bind;

import android.util.Log;
import com.benny.library.neobinding.converter.EmptyTwoWayConverter;
import com.benny.library.neobinding.converter.TwoWayPropertyConverter;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.Subject;


/**
 * Created by benny on 11/5/15.
 */
public class TwoWayPropertyBinding<T, R> {
    private String property;
    private Observable<T> observable;
    private Action1<? super T> observer;

    private TwoWayPropertyConverter<T, ?> converter;

    public TwoWayPropertyBinding(String property, Observable<T> observable, Action1<? super T> observer) {
        this.property = property;
        this.observable = observable;
        this.observer = observer;

        this.converter = new EmptyTwoWayConverter<T, T>();
    }

    public TwoWayPropertyBinding(String property, TwoWayPropertyConverter<T, R> converter, Observable<T> observable, Action1<? super T> observer) {
        this.property = property;
        this.observable = observable;
        this.observer = observer;

        this.converter = converter;
    }

    public String property() {
        return property;
    }

    public void bindTo(BindingContext<?> bindingContext, Subject<R, R> subject) {
        CircleBreaker breaker = new CircleBreaker();

        observable.doOnNext(o -> Log.d("TwoWayPropertyBinding", "view->model:" + o.toString())).filter(breaker).map(((TwoWayPropertyConverter<T, R>) converter)::convertBack).compose(bindingContext.applyLifecycle())
                .doOnSubscribe(() -> ViewModelBinder.LogBinding(property + " view->model"))
                .doOnTerminate(() -> ViewModelBinder.LogUnbinding(property + " view->model"))
                .doOnError(e->Log.d("Binding", "view->model error:" + e))
                .subscribe(subject);
        subject.map(converter::convert).doOnNext(o -> Log.d("TwoWayPropertyBinding", "model->view:" + o.toString())).filter(breaker).compose(bindingContext.applyLifecycle())
                .doOnSubscribe(() -> ViewModelBinder.LogBinding(property + " model->view"))
                .doOnTerminate(() -> ViewModelBinder.LogUnbinding(property + " model->view"))
                .doOnError(e->Log.d("Binding", "model->view error:" + e))
                .subscribe(observer);
    }

    private static class CircleBreaker implements Func1<Object, Boolean> {
        private Object last;
        @Override
        public Boolean call(Object o) {
            if(last != null && o.toString().equals(last.toString())) return false;
            last = o;
            return true;
        }
    }
}

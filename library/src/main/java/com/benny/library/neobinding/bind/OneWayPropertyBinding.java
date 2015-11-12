package com.benny.library.neobinding.bind;

import android.util.Log;
import com.benny.library.neobinding.converter.EmptyConverter;
import com.benny.library.neobinding.converter.MultiplePropertyConverter;
import com.benny.library.neobinding.converter.OneWayPropertyConverter;
import org.apache.commons.lang3.StringUtils;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.Subject;

import java.util.List;

/**
 * Created by benny on 11/5/15.
 */
public class OneWayPropertyBinding<T> {
    private String property;
    private List<String> properties;

    private OneWayPropertyConverter<?> converter;
    private MultiplePropertyConverter<T> multipleConverter;

    private Action1<? super T> observer;
    private Observable<T> observable;

    public OneWayPropertyBinding(String property, Action1<T> observer) {
        this.property = property;
        this.converter = new EmptyConverter<T>();
        this.observer = observer;
    }

    public OneWayPropertyBinding(String property, OneWayPropertyConverter<T> converter, Action1<? super T> observer) {
        this.property = property;
        this.converter = converter;
        this.observer = observer;
    }

    public OneWayPropertyBinding(String property, Observable<T> observable) {
        this.property = property;
        this.converter = new EmptyConverter<T>();
        this.observable = observable;
    }

    public OneWayPropertyBinding(String property, OneWayPropertyConverter<?> converter, Observable<T> observable) {
        this.property = property;
        this.converter = converter;
        this.observable = observable;
    }

    public OneWayPropertyBinding(List<String> properties, MultiplePropertyConverter<T> multipleConverter, Action1<? super T> observer) {
        this.properties = properties;
        this.multipleConverter = multipleConverter;
        this.observer = observer;
    }

    public String property() {
        return property != null ? property : properties.get(0);
    }

    public List<String> properties() {
        return properties;
    }

    public void bindTo(BindingContext<?> bindingContext, List<Observable<?> > observables) {
        Observable.combineLatest(observables, multipleConverter::convert).compose(bindingContext.applyLifecycle())
                .doOnSubscribe(() -> ViewModelBinder.LogBinding(StringUtils.join(properties, ",") + " multiple"))
                .doOnTerminate(() -> ViewModelBinder.LogUnbinding(StringUtils.join(properties, ",") + " multiple"))
                .doOnNext(o-> Log.d("Binding", "multiple onNext:" + o))
                        .subscribe(observer);
    }

    @SuppressWarnings("unchecked")
    public void bindTo(BindingContext<?> bindingContext, Subject subject) {
        if(observable != null) {
            observable.map((Func1<T, Object>)converter::convert).compose(bindingContext.applyLifecycle())
                    .doOnSubscribe(() -> ViewModelBinder.LogBinding(property + " view->model"))
                    .doOnTerminate(() -> ViewModelBinder.LogUnbinding(property + " view->model"))
                    .subscribe(subject);
        }
        else {
            subject.map(converter::convert).compose(bindingContext.applyLifecycle())
                    .doOnSubscribe(() -> ViewModelBinder.LogBinding(property + " model->view"))
                    .doOnTerminate(() -> ViewModelBinder.LogUnbinding(property + " model->view"))
                    .subscribe(observer);
        }
    }
}

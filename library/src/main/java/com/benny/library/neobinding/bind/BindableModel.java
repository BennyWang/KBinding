package com.benny.library.neobinding.bind;

import rx.Observable;
import rx.subjects.BehaviorSubject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by benny on 11/5/15.
 */

public abstract class BindableModel<T> {
    public static final String PROPERTY_ITEM_POSITION = "property_item_position";

    private Map<String, BehaviorSubject<?> > propertyMapper = new HashMap<>();
    private Map<String, Command<?>> commandMapper = new HashMap<>();

    public BindableModel() {
        initCommand();
    }

    protected void addProperty(String key, BehaviorSubject<?> property) {
        propertyMapper.put(key, property);
    }

    protected void addCommand(String key, Command<?> command) {
        commandMapper.put(key, command);
    }

    protected <R> BehaviorSubject<R> property(String key) {
        return (BehaviorSubject<R>)propertyMapper.get(key);
    }

    protected <R> Command<R> command(String key) {
        return (Command<R>)commandMapper.get(key);
    }

    private void ensurePropertyInitialized(String key) {
        if(property(key) == null || property(key).hasCompleted()) {
            addProperty(PROPERTY_ITEM_POSITION, BehaviorSubject.<Integer>create());
            initProperty();
        }
    }

    public void bindProperty(BindingContext bindingContext, OneWayPropertyBinding<?> observer) {
        ensurePropertyInitialized(observer.property());
        observer.bindTo(bindingContext, property(observer.property()));
    }

    public void bindProperty(BindingContext bindingContext, TwoWayPropertyBinding<?, ?> observable) {
        ensurePropertyInitialized(observable.property());
        observable.bindTo(bindingContext, property(observable.property()));
    }

    public void bindProperties(BindingContext bindingContext, OneWayPropertyBinding<?> observer) {
        ensurePropertyInitialized(observer.property());
        List<Observable<?>> props = new ArrayList<>();
        for(String key : observer.properties()) {
            if(property(key) != null) props.add(property(key));
        }
        observer.bindTo(bindingContext, props);
    }

    public void bindCommand(BindingContext bindingContext, CommandBinding<?> observable) {
        observable.bindTo(bindingContext, command(observable.property()));
    }

    final public void notifyPropertyChange(T t, int position) {
        notifyPropertyChange(t);
        property(PROPERTY_ITEM_POSITION).onNext(position);
    }

    abstract public void notifyPropertyChange(T t);

    abstract protected void initProperty();
    abstract protected void initCommand();
}

package com.benny.library.neobinding.bind;

import android.util.Log;
import android.view.View;
import com.benny.library.neobinding.factory.Factory;

/**
 * Created by benny on 11/5/15.
 */

public class ViewModelBinder<T> {
    private static int bindCount = 0;

    BindingContext<?> bindingContext;
    private Factory<BindableModel<T> > bindableModelFactory;
    private Factory<BindableView> bindableViewFactory;

    public static void LogBinding(String property) {
        ++bindCount;
        Log.d("Binding", "Binding for {" + property + "}. Binding count is " + bindCount);
    }

    public static void LogUnbinding(String property) {
        --bindCount;
        Log.d("Binding", "Unbinding for {" + property + "}. Binding count is " + bindCount);
    }

    public static <T> ViewModelBinder<T> create(BindingContext<?> bindingContext, Factory<BindableModel<T> > bindableModelFactory, Factory<BindableView> bindableViewFactory) {
        return new ViewModelBinder<>(bindingContext, bindableModelFactory, bindableViewFactory);
    }

    private ViewModelBinder(BindingContext<?> bindingContext, Factory<BindableModel<T> > bindableModelFactory, Factory<BindableView> bindableViewFactory) {
        this.bindingContext = bindingContext;
        this.bindableModelFactory = bindableModelFactory;
        this.bindableViewFactory = bindableViewFactory;
    }

    public BindableModel<T> bind(View view) {
        BindableModel<T> bindableModel = bindableModelFactory.create();
        BindableView bindableView = bindableViewFactory.create();

        bindableView.inject(bindingContext, view);

        for (OneWayPropertyBinding observer : bindableView.oneWayProperties()) {
            bindableModel.bindProperty(bindingContext, observer);
        }

        for (OneWayPropertyBinding observer : bindableView.multipleProperties()) {
            bindableModel.bindProperties(bindingContext, observer);
        }

        for (TwoWayPropertyBinding observable : bindableView.twoWayProperties()) {
            bindableModel.bindProperty(bindingContext, observable);
        }

        for (CommandBinding observable : bindableView.commands()) {
            bindableModel.bindCommand(bindingContext, observable);
        }

        return bindableModel;
    }
}

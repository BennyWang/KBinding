package com.benny.library.neobinding.bind;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benny on 11/5/15.
 */

public abstract class BindableView {
    private List<OneWayPropertyBinding<?>> oneWayPropertyBindings = new ArrayList<>();
    private List<TwoWayPropertyBinding<?, ?>> twoWayPropertyBindings = new ArrayList<>();
    private List<OneWayPropertyBinding<?>> multiplePropertyBindings = new ArrayList<>();
    List<CommandBinding<?> > commandBindings = new ArrayList<>();

    public List<OneWayPropertyBinding<?>> oneWayProperties() {
        return oneWayPropertyBindings;
    }

    public List<TwoWayPropertyBinding<?, ?>> twoWayProperties() {
        return twoWayPropertyBindings;
    }

    public List<OneWayPropertyBinding<?>> multipleProperties() {
        return multiplePropertyBindings;
    }

    public List<CommandBinding<?> > commands() {
        return commandBindings;
    }

    protected void addOneWayProperty(OneWayPropertyBinding<?> oneWayPropertyBinding) {
        oneWayPropertyBindings.add(oneWayPropertyBinding);
    }

    protected void addTwoWayProprty(TwoWayPropertyBinding<?, ?> twoWayPropertyBinding) {
        twoWayPropertyBindings.add(twoWayPropertyBinding);
    }

    protected void addMultipleProperty(OneWayPropertyBinding<?> multiplePropertyBinding) {
        multiplePropertyBindings.add(multiplePropertyBinding);
    }

    protected void addCommand(CommandBinding<?> commandBinding) {
        commandBindings.add(commandBinding);
    }

    public abstract void inject(BindingContext bindingContext, View view);
}

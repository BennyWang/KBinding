package com.benny.library.neobinding.bind;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by benny on 11/5/15.
 */

public class ViewCreator<T> {
    private ViewModelBinder<T> viewModelBinder;
    private int layoutId;

    public static <T> ViewCreator<T> create(ViewModelBinder<T> viewModelBinder, int layoutId) {
        return new ViewCreator<>(viewModelBinder, layoutId);
    }

    public ViewCreator() {
    }

    private ViewCreator(ViewModelBinder<T> viewModelBinder, int layoutId) {
        this.viewModelBinder = viewModelBinder;
        this.layoutId = layoutId;
    }

    public View view(ViewGroup container) {
        View view = LayoutInflater.from(container.getContext()).inflate(layoutId, container, false);
        BindableModel<T> bindableModel = viewModelBinder.bind(view);
        view.setTag(bindableModel);
        return view;
    }
}

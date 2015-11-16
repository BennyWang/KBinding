package com.benny.library.neobinding.bind;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by benny on 9/16/15.
 *
 * For Adapter View has multiple type item view
 *
 */
public abstract class ViewCreatorCollection<T> {
    private int viewTypeBegin = 0;

    private List<ViewTypeFilter<T> > viewTypeFilters = new ArrayList<>();

    int lastViewType = -1;

    public ViewCreatorCollection() {
    }

    public ViewTypeFilter<T> filter(T data, int position) {
        for (ViewTypeFilter<T> viewTypeFilter : viewTypeFilters) {
            if(viewTypeFilter.canProcess(data, position)) return viewTypeFilter;
        }
        return null;
    }

    public View view(ViewGroup container) {
        int index = lastViewType != -1 ? lastViewType : viewTypeFilters.size() - 1;
        return viewTypeFilters.get(index).view(container);
    }

    public int viewTypeFor(T data, int position) {
        return lastViewType = filter(data, position).getViewType();
    }

    public int typeCount() {
        return viewTypeFilters.size();
    }

    public final ViewCreatorCollection append(Func2<T, Integer, Boolean> filter, ViewCreator<?> viewCreator) {
        viewTypeFilters.add(new ViewTypeFilter<>(filter, viewCreator).setViewType(viewTypeBegin++));
        return this;
    }

    private static class ViewTypeFilter<T> {
        int viewType;
        ViewCreator<?> creator;
        Func2<T, Integer, Boolean> filter;

        public ViewTypeFilter(Func2<T, Integer, Boolean> filter, ViewCreator<?> creator) {
            this.creator = creator;
            this.filter = filter;
        }

        ViewTypeFilter<T> setViewType(int viewType) {
            this.viewType = viewType;
            return this;
        }

        int getViewType() {
            return viewType;
        }

        public boolean canProcess(T data, int position) {
            return filter.call(data, position);
        }

        public View view(ViewGroup container) {
            return creator.view(container);
        }
    }
}

package com.benny.library.neobinding.converter;

import android.widget.BaseAdapter;

import com.benny.library.neobinding.adapter.AdapterUtils;
import com.benny.library.neobinding.adapter.SampleAdapterItemAccessor;
import com.benny.library.neobinding.bind.ViewCreatorCollection;
import com.benny.library.neobinding.bind.ViewCreator;

import java.util.List;

/**
 * Created by benny on 11/8/15.
 */
public class ListToAdapterConverter<T> implements OneWayPropertyConverter<BaseAdapter> {
    private ViewCreator<T> viewCreator;
    private ViewCreatorCollection<T> viewCreatorCollection;

    public ListToAdapterConverter(ViewCreator<T> viewCreator) {
        this.viewCreator = viewCreator;
    }

    public ListToAdapterConverter(ViewCreatorCollection<T> viewCreatorCollection) {
        this.viewCreatorCollection = viewCreatorCollection;
    }

    @Override
    public BaseAdapter convert(Object var) {
        return viewCreator != null ? AdapterUtils.toAdapter(viewCreator).call(new SampleAdapterItemAccessor<>((List<T>)var)) :
                AdapterUtils.toAdapter(viewCreatorCollection).call(new SampleAdapterItemAccessor<>((List<T>)var));
    }
}

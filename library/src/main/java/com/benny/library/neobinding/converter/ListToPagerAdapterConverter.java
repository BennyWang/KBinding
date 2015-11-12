package com.benny.library.neobinding.converter;

import android.support.v4.view.PagerAdapter;

import com.benny.library.neobinding.adapter.AdapterUtils;
import com.benny.library.neobinding.adapter.SampleAdapterItemAccessor;
import com.benny.library.neobinding.bind.ViewCreator;

import java.util.List;

/**
 * Created by benny on 11/8/15.
 */
public class ListToPagerAdapterConverter<T> implements OneWayPropertyConverter<PagerAdapter> {
    private ViewCreator<T> viewCreator;

    public ListToPagerAdapterConverter(ViewCreator<T> viewCreator) {
        this.viewCreator = viewCreator;
    }


    @Override
    public PagerAdapter convert(Object var) {
        return AdapterUtils.toPagerAdapter(viewCreator).call(new SampleAdapterItemAccessor<>((List<T>)var));
    }
}

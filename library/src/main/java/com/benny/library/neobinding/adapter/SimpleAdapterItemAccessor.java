package com.benny.library.neobinding.adapter;

import java.util.List;

/**
 * Created by benny on 9/18/15.
 */
public class SimpleAdapterItemAccessor<T> implements AdapterItemAccessor<T> {
    private List<T> list;

    public SimpleAdapterItemAccessor(List<T> list) {
        this.list = list;
    }

    public int size() {
        return list.size();
    }
    public T get(int position) {
        return list.get(position);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}

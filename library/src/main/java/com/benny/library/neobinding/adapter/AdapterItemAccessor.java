package com.benny.library.neobinding.adapter;

/**
 * Created by benny on 9/18/15.
 */
public interface AdapterItemAccessor<T> {
    int size();
    T get(int position);
    boolean isEmpty();
}

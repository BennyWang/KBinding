package com.benny.library.kbinding.bind;

/**
 * Created by benny on 3/7/16.
 */

public interface BindingBuilder<T> {
    void build(final T target);
}
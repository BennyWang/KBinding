package com.benny.library.kbinding.internal;

import kotlin.jvm.functions.Function0;

/**
 * Created by benny on 3/3/16.
 */

public interface BindingBuilder<T> {
    void build(final T target);
}

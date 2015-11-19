package com.benny.library.neobinding.kotlin.factory

import com.benny.library.neobinding.kotlin.adapter.AdapterItemAccessor
import com.benny.library.neobinding.kotlin.adapter.SimpleAdapterItemAccessor

/**
 * Created by benny on 11/18/15.
 */

public interface Factory<T> {
    fun create(): T
}

public interface Factory1<T, R> {
    fun create(input: T): R
}

public class SimpleAdapterItemAccessorFactory<T> : Factory1<List<T>, AdapterItemAccessor<T>> {
    override fun create(input: List<T>): SimpleAdapterItemAccessor<T> {
        return SimpleAdapterItemAccessor(input)
    }

}
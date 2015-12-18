package com.benny.library.neobinding.adapter

/**
 * Created by benny on 11/19/15.
 */
interface AdapterItemAccessor<T> {
    fun size(): Int
    fun get(position: Int): T?
    fun isEmpty(): Boolean
    fun swap(itemAccessor: AdapterItemAccessor<T>): Unit
}
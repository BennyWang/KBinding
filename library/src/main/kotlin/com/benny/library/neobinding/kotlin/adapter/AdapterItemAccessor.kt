package com.benny.library.neobinding.kotlin.adapter

/**
 * Created by benny on 11/19/15.
 */
interface AdapterItemAccessor {
    fun size(): Int
    fun get(position: Int): Any
    fun isEmpty(): Boolean
}
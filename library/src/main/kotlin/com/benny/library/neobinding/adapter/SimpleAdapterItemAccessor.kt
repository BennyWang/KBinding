package com.benny.library.neobinding.adapter

/**
 * Created by benny on 9/18/15.
 */
class SimpleAdapterItemAccessor<T> (private val list: List<T>) : AdapterItemAccessor<T> {

    override fun size(): Int {
        return list.size
    }

    override fun get(position: Int): T? {
        return list.getOrNull(position)
    }

    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }
}

package com.benny.library.neobinding.kotlin.adapter

/**
 * Created by benny on 9/18/15.
 */
class SimpleAdapterItemAccessor(private val list: List<*>) : AdapterItemAccessor {

    override fun size(): Int {
        return list.size
    }

    override fun get(position: Int): Any {
        return list[position] as Any
    }

    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }
}

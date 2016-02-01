package com.benny.library.kbinding.support.v4.adapter

import com.benny.library.kbinding.common.adapter.AdapterItemAccessor
import com.benny.library.kbinding.common.adapter.SimpleAdapterItemAccessor

/**
 * Created by benny on 2/1/16.
 */

open class SimplePagerAdapterItemAccessor<T>(private var list: List<T>) : SimpleAdapterItemAccessor<T>(list), PagerAdapterItemAccessor<T> {
    override fun getTitle(position: Int): String {
        return ""
    }
}
package com.benny.library.kbinding.support.v4.adapter

import com.benny.library.kbinding.common.adapter.AdapterItemAccessor

/**
 * Created by benny on 2/1/16.
 */

interface PagerAdapterItemAccessor<T> : AdapterItemAccessor<T> {
    fun getTitle(position: Int) : String
}
package com.benny.library.neobinding.adapter

/**
 * Created by benny on 11/19/15.
 */
interface AdapterPagingListener<T> {
    fun onLoadPage(previous: T?, position: Int);
}
package com.benny.library.kbinding.common.converter

import android.widget.ListAdapter
import com.benny.library.kbinding.common.adapter.AdapterItemAccessor
import com.benny.library.kbinding.common.adapter.BaseListAdapter
import com.benny.library.kbinding.common.adapter.BaseListPagingAdapter
import com.benny.library.kbinding.common.adapter.SimpleAdapterItemAccessor
import com.benny.library.kbinding.converter.OneWayConverter
import com.benny.library.kbinding.view.IViewCreator

/**
 * Created by benny on 1/30/16.
 */

class ListToAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: (List<T>) -> AdapterItemAccessor<T> = { list -> SimpleAdapterItemAccessor(list) } ) : OneWayConverter<List<T>, ListAdapter> {
    override fun convert(source: List<T>): ListAdapter {
        return BaseListAdapter(viewCreator, itemAccessorFactory(source))
    }
}

class ListToPagingAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: (List<T>) -> AdapterItemAccessor<T> = { list -> SimpleAdapterItemAccessor(list) } ) : OneWayConverter<List<T>, ListAdapter> {
    override fun convert(source: List<T>): ListAdapter {
        return BaseListPagingAdapter(viewCreator, itemAccessorFactory(source))
    }
}

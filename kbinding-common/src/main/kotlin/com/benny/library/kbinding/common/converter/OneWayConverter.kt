package com.benny.library.kbinding.common.converter

import android.widget.ListAdapter
import com.benny.library.kbinding.common.adapter.*
import com.benny.library.kbinding.converter.OneWayConverter
import com.benny.library.kbinding.view.IViewCreator

/**
 * Created by benny on 1/30/16.
 */

class ListToAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: (List<T>) -> AdapterItemAccessor<T> = { list -> SimpleAdapterItemAccessor(list) } ) : OneWayConverter<ListAdapter> {
    override fun convert(source: Any): ListAdapter {
        return BaseListAdapter(viewCreator, itemAccessorFactory(source as List<T>))
    }
}

class ListToPagingAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: (List<T>) -> AdapterItemAccessor<T> = { list -> SimpleAdapterItemAccessor(list) } ) : OneWayConverter<ListAdapter> {
    override fun convert(source: Any): ListAdapter {
        return BaseListPagingAdapter(viewCreator, itemAccessorFactory(source as List<T>))
    }
}

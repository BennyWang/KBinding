@file:Suppress("UNCHECKED_CAST")

package com.benny.library.kbinding.converter

import android.support.v7.widget.RecyclerView
import android.widget.ListAdapter
import com.benny.library.kbinding.adapter.*
import com.benny.library.kbinding.view.IViewCreator

/**
 * Created by benny on 11/18/15.
 */

interface OneWayConverter<T> {
    fun convert(source: Any): T
}

class EmptyOneWayConverter<T> : OneWayConverter<T> {
    override fun convert(source: Any): T {
        return source as T
    }
}

class StringConverter : OneWayConverter<String> {
    override fun convert(source: Any): String {
        return source.toString()
    }
}

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

class ListToRecyclerAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: (List<T>) -> AdapterItemAccessor<T> = { list -> SimpleAdapterItemAccessor(list) } ) : OneWayConverter<RecyclerView.Adapter<RecyclerView.ViewHolder>> {
    override fun convert(source: Any): BaseRecyclerAdapter<T> {
        return BaseRecyclerAdapter(viewCreator, itemAccessorFactory(source as List<T>))
    }
}

class ListToRecyclerPagingAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: (List<T>) -> AdapterItemAccessor<T> = { list -> SimpleAdapterItemAccessor(list) } ) : OneWayConverter<RecyclerView.Adapter<RecyclerView.ViewHolder>> {
    override fun convert(source: Any): BaseRecyclerPagingAdapter<T> {
        return BaseRecyclerPagingAdapter(viewCreator, itemAccessorFactory(source as List<T>))
    }
}
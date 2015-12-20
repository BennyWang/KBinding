package com.benny.library.neobinding.converter

import android.support.v7.widget.RecyclerView
import android.widget.ListAdapter
import com.benny.library.neobinding.adapter.*
import com.benny.library.neobinding.view.IViewCreator

/**
 * Created by benny on 11/18/15.
 */

public interface OneWayConverter<T> {
    fun convert(source: Any?): T
}

public class EmptyOneWayConverter<T> : OneWayConverter<T> {
    override fun convert(source: Any?): T {
        return source as T
    }
}

public class StringConverter : OneWayConverter<String> {
    override fun convert(source: Any?): String {
        return if(source == null) "" else source.toString()
    }
}

class ListToAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: (List<T>) -> AdapterItemAccessor<T> = { list -> SimpleAdapterItemAccessor(list) } ) : OneWayConverter<ListAdapter> {
    override fun convert(source: Any?): ListAdapter {
        return BaseListAdapter(viewCreator, itemAccessorFactory(source as List<T>))
    }
}

class ListToPagingAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: (List<T>) -> AdapterItemAccessor<T> = { list -> SimpleAdapterItemAccessor(list) } ) : OneWayConverter<ListAdapter> {
    override fun convert(source: Any?): ListAdapter {
        return BaseListPagingAdapter(viewCreator, itemAccessorFactory(source as List<T>))
    }
}

class ListToRecyclerAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: (List<T>) -> AdapterItemAccessor<T> = { list -> SimpleAdapterItemAccessor(list) } ) : OneWayConverter<BaseRecyclerAdapter<T>> {
    override fun convert(source: Any?): BaseRecyclerAdapter<T> {
        return BaseRecyclerAdapter(viewCreator, itemAccessorFactory(source as List<T>))
    }
}

class ListToRecyclerPagingAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: (List<T>) -> AdapterItemAccessor<T> = { list -> SimpleAdapterItemAccessor(list) } ) : OneWayConverter<BaseRecyclerPagingAdapter<T>> {
    override fun convert(source: Any?): BaseRecyclerPagingAdapter<T> {
        return BaseRecyclerPagingAdapter(viewCreator, itemAccessorFactory(source as List<T>))
    }
}
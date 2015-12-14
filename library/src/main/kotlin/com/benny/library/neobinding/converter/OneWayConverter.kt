package com.benny.library.neobinding.converter

import android.support.v7.widget.RecyclerView
import com.benny.library.neobinding.adapter.*
import com.benny.library.neobinding.view.IViewCreator

/**
 * Created by benny on 11/18/15.
 */

public interface OneWayConverter<T> {
    fun convert(source: Any): T
}

public class EmptyOneWayConverter<T> : OneWayConverter<T> {
    override fun convert(source: Any): T {
        return source as T
    }
}

class ListToAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: (List<T>) -> AdapterItemAccessor<T> = { list -> SimpleAdapterItemAccessor(list) } ) : OneWayConverter<RecyclerView.Adapter<RecyclerView.ViewHolder>> {
    override fun convert(`var`: Any): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return AdapterUtils.toAdapter(viewCreator, itemAccessorFactory(`var` as List<T>))
    }
}

class ListToPagingAdapterConverter<T>(val viewCreator: IViewCreator<T>, val adapterPagingListener: AdapterPagingListener<T>, val itemAccessorFactory: (List<T>) -> AdapterItemAccessor<T> = { list -> SimpleAdapterItemAccessor(list) } ) : OneWayConverter<RecyclerPagingAdapter<T> > {
    override fun convert(`var`: Any): RecyclerPagingAdapter<T> {
        return AdapterUtils.toPagingAdapter(viewCreator, itemAccessorFactory(`var` as List<T>), adapterPagingListener)
    }
}
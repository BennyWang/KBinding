package com.benny.library.neobinding.kotlin.converter

import android.support.v7.widget.RecyclerView
import com.benny.library.neobinding.kotlin.adapter.*
import com.benny.library.neobinding.kotlin.bind.IViewCreator
import com.benny.library.neobinding.kotlin.factory.Factory
import com.benny.library.neobinding.kotlin.factory.Factory1
import com.benny.library.neobinding.kotlin.factory.SimpleAdapterItemAccessorFactory

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

class ListToAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: Factory1<List<T>, AdapterItemAccessor<T>> = SimpleAdapterItemAccessorFactory<T>() ) : OneWayConverter<RecyclerView.Adapter<RecyclerView.ViewHolder>> {
    override fun convert(`var`: Any): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return AdapterUtils.toAdapter(viewCreator, itemAccessorFactory.create(`var` as List<T>))
    }
}

class ListToPagingAdapterConverter<T>(val viewCreator: IViewCreator<T>, val adapterPagingListener: AdapterPagingListener<T>, val itemAccessorFactory: Factory1<List<T>, AdapterItemAccessor<T>> = SimpleAdapterItemAccessorFactory<T>()) : OneWayConverter<RecyclerPagingAdapter<T> > {
    override fun convert(`var`: Any): RecyclerPagingAdapter<T> {
        return AdapterUtils.toPagingAdapter(viewCreator, itemAccessorFactory.create(`var` as List<T>), adapterPagingListener)
    }
}
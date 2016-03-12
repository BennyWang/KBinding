package com.benny.library.kbinding.adapterview.converter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.RecyclerView
import android.widget.ListAdapter
import com.benny.library.autoadapter.*
import com.benny.library.autoadapter.viewcreator.IViewCreator
import com.benny.library.kbinding.converter.OneWayConverter

/**
 * Created by benny on 1/30/16.
 */

class ListToAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: (List<T>) -> IAdapterItemAccessor<T> = { list -> SimpleAdapterItemAccessor(list) } ) : OneWayConverter<List<T>, ListAdapter> {
    override fun convert(source: List<T>): ListAdapter {
        return AutoListAdapter(itemAccessorFactory(source), viewCreator)
    }
}

class ListToPagingAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: (List<T>) -> IAdapterItemAccessor<T> = { list -> SimpleAdapterItemAccessor(list) } ) : OneWayConverter<List<T>, ListAdapter> {
    override fun convert(source: List<T>): ListAdapter {
        return AutoListPagingAdapter(itemAccessorFactory(source), viewCreator)
    }
}

class ListToRecyclerAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: (List<T>) -> IAdapterItemAccessor<T> = { list -> SimpleAdapterItemAccessor(list) } ) : OneWayConverter<List<T>, RecyclerView.Adapter<RecyclerView.ViewHolder>> {
    override fun convert(source: List<T>): AutoRecyclerAdapter<T> {
        return AutoRecyclerAdapter(itemAccessorFactory(source), viewCreator)
    }
}

class ListToRecyclerPagingAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: (List<T>) -> IAdapterItemAccessor<T> = { list -> SimpleAdapterItemAccessor(list) } ) : OneWayConverter<List<T>, RecyclerView.Adapter<RecyclerView.ViewHolder>> {
    override fun convert(source: List<T>): AutoRecyclerPagingAdapter<T> {
        return AutoRecyclerPagingAdapter(itemAccessorFactory(source), viewCreator)
    }
}

class ListToPagerAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: (List<T>) -> IAdapterItemAccessor<T> = { list -> SimpleAdapterItemAccessor(list) } ) : OneWayConverter<List<T>, PagerAdapter> {
    override fun convert(source: List<T>): PagerAdapter {
        return AutoPagerAdapter(itemAccessorFactory(source), viewCreator)
    }
}

class ListToFragmentPagerAdapterConverter(val FragmentManager: FragmentManager, val itemAccessorFactory: (List<Fragment>) -> IAdapterItemAccessor<Fragment> = { list -> SimpleAdapterItemAccessor(list) } ) : OneWayConverter<List<Fragment>, FragmentPagerAdapter> {
    override fun convert(source: List<Fragment>): FragmentPagerAdapter {
        return AutoFragmentPagerAdapter(FragmentManager, itemAccessorFactory(source))
    }
}
package com.benny.library.neobinding.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.benny.library.neobinding.bind.ItemViewModel
import com.benny.library.neobinding.view.IViewCreator

/**
 * Created by benny on 9/17/15.
 */


class RecyclerPagingAdapter<T>(val viewCreator: IViewCreator<T>, val itemAccessor: AdapterItemAccessor<T>, listener: AdapterPagingListener<T>) : BaseRecyclerPagingAdapter<T>(listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        val view = viewCreator.view(parent)
        return createViewHolder(view)
    }

    override fun getItem(position: Int): T? {
        return itemAccessor.get(position)
    }

    override fun getCount(): Int {
        return itemAccessor.size()
    }

    override fun getItemViewType(position: Int): Int {
        return viewCreator.viewTypeFor(getItem(position), position)
    }

}

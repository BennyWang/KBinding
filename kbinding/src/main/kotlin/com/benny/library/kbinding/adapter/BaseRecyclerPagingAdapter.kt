package com.benny.library.kbinding.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.benny.library.kbinding.bind.ItemViewModel
import com.benny.library.kbinding.view.IViewCreator

/**
 * Created by benny on 12/18/15.
 */

class BaseRecyclerPagingAdapter<T> (viewCreator: IViewCreator<T>, itemAccessor: AdapterItemAccessor<T>) : BaseRecyclerAdapter<T>(viewCreator, itemAccessor) {
    private var hasNextPage = true
    private var loading = false

    public var pagingListener: AdapterPagingListener? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ViewHolder<T>)?.notifyPropertyChange(itemAccessor.get(position), position)

        if (position == itemCount - 1 && hasNextPage && !loading) {
            loading = true
            pagingListener?.onLoadPage(itemAccessor.get(position - 1), position)
        }
    }

    override fun getItemCount(): Int {
        return if (hasNextPage) super.getItemCount() + 1 else super.getItemCount()
    }

    fun loadComplete(hasNextPage: Boolean) {
        this.hasNextPage = hasNextPage
        loading = false
        notifyDataSetChanged()
    }
}

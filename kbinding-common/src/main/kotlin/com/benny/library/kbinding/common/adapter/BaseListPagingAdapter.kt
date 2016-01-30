package com.benny.library.kbinding.common.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.benny.library.kbinding.common.adapter.AdapterItemAccessor
import com.benny.library.kbinding.common.adapter.AdapterPagingListener
import com.benny.library.kbinding.common.adapter.BaseListAdapter
import com.benny.library.kbinding.bind.ItemViewModel
import com.benny.library.kbinding.view.IViewCreator

/**
 * Created by benny on 12/18/15.
 */
class BaseListPagingAdapter<T>(viewCreator: IViewCreator<T>, itemAccessor: AdapterItemAccessor<T>) : BaseListAdapter<T>(viewCreator, itemAccessor) {
    private var hasNextPage = true
    private var loading = false

    var pagingListener: AdapterPagingListener? = null

    @Suppress("UNCHECKED_CAST")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var itemView = convertView
        if(itemView == null) {
            itemView = viewCreator.view(parent)
        }
        ((itemView.tag) as? ItemViewModel<T>)?.notifyPropertyChange(getItem(position), position)

        if (position == count - 1 && hasNextPage && !loading) {
            loading = true
            pagingListener?.onLoadPage(itemAccessor.get(position - 1), position)
        }
        return itemView
    }

    override fun getCount(): Int {
        return if (hasNextPage) super.getCount() + 1 else super.getCount()
    }

    fun loadComplete(hasNextPage: Boolean) {
        this.hasNextPage = hasNextPage
        loading = false
        notifyDataSetChanged()
    }
}
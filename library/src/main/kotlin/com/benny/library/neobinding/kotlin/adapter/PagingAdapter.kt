package com.benny.library.neobinding.kotlin.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.benny.library.neobinding.adapter.AdapterPagingListener


/**
 * Created by benny on 9/17/15.
 */

abstract class PagingAdapter(internal var listener: AdapterPagingListener) : BaseAdapter() {
    private var hasNextPage = true

    override fun getCount(): Int {
        return if (hasNextPage) countBase() + 1 else countBase()
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, contentView: View, parent: ViewGroup): View {
        var view = getViewBase(position, contentView, parent)

        if (position == count - 1 && hasNextPage) {
            listener.onLoadPage(getItem(position - 1), position)
        }
        return view
    }

    fun loadComplete(hasNextPage: Boolean) {
        this.hasNextPage = hasNextPage
        notifyDataSetChanged()
    }

    abstract fun getViewBase(position: Int, convertView: View, parent: ViewGroup): View
    abstract fun countBase(): Int

    abstract override fun getItem(position: Int): Any

}

package com.benny.library.kbinding.common.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.benny.library.kbinding.viewmodel.ItemViewModel
import com.benny.library.kbinding.view.IViewCreator

/**
 * Created by benny on 12/18/15.
 */
open class BaseListAdapter<T>(val viewCreator: IViewCreator<T>, val itemAccessor: AdapterItemAccessor<T>) : BaseAdapter() {
    @Suppress("UNCHECKED_CAST")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var itemView = convertView
        if(itemView == null) {
            itemView = viewCreator.view(parent)
        }
        ((itemView.tag) as? ItemViewModel<T>)?.notifyPropertyChange(getItem(position), position)
        return itemView
    }

    override fun getItem(position: Int): T? {
        return itemAccessor.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return itemAccessor.size()
    }

    fun swap(t: BaseListAdapter<T>) {
        itemAccessor.swap(t.itemAccessor)
        notifyDataSetChanged()
    }
}
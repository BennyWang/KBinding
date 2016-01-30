package com.benny.library.kbinding.support.v7.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.benny.library.kbinding.common.adapter.AdapterItemAccessor
import com.benny.library.kbinding.bind.ItemViewModel
import com.benny.library.kbinding.view.IViewCreator

/**
 * Created by benny on 12/18/15.
 */

open class BaseRecyclerAdapter<T> (val viewCreator: IViewCreator<T>, val itemAccessor: AdapterItemAccessor<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @Suppress("UNCHECKED_CAST")
        fun notifyPropertyChange(data: T?, position: Int) {
            ((itemView.tag) as? ItemViewModel<T>)?.notifyPropertyChange(data, position)
        }
    }

    protected fun createViewHolder(itemView: View) : RecyclerView.ViewHolder {
        return ViewHolder<T>(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        Log.d("BaseRecyclerAdapter", "onCreateViewHolder")
        return createViewHolder(viewCreator.view(parent))
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("BaseRecyclerAdapter", "onBindViewHolder position is " + position)
        (holder as ViewHolder<T>).notifyPropertyChange(itemAccessor.get(position), position)
    }

    override fun getItemViewType(position: Int): Int {
        return viewCreator.viewTypeFor(itemAccessor.get(position), position)
    }

    override fun getItemCount(): Int {
        Log.d("BaseRecyclerAdapter", "get count is " + itemAccessor.size())
        return itemAccessor.size()
    }

    fun swap(t: BaseRecyclerAdapter<T>) {
        itemAccessor.swap(t.itemAccessor)
        notifyDataSetChanged()
    }
}
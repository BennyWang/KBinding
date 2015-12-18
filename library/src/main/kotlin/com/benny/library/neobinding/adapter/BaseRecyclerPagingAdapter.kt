package com.benny.library.neobinding.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.benny.library.neobinding.bind.ItemViewModel

/**
 * Created by benny on 12/18/15.
 */

abstract class BaseRecyclerPagingAdapter<T> (val listener: AdapterPagingListener<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var hasNextPage = true

    protected class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun notifyPropertyChange(data: T?, position: Int) {
            var tag = itemView.tag
            if (tag != null && tag is ItemViewModel<*>) {
                (tag as? ItemViewModel<T>)?.notifyPropertyChange(data, position)
            }
        }
    }

    protected fun createViewHolder(itemView: View) : RecyclerView.ViewHolder {
        return ViewHolder<T>(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ViewHolder<T>)?.notifyPropertyChange(getItem(position), position)

        if (position == itemCount - 1 && hasNextPage) {
            listener.onLoadPage(getItem(position - 1), position)
        }
    }

    override fun getItemCount(): Int {
        return if (hasNextPage) getCount() + 1 else getCount()
    }

    fun loadComplete(hasNextPage: Boolean) {
        this.hasNextPage = hasNextPage
        notifyDataSetChanged()
    }

    abstract fun getItem(position: Int): T?
    abstract fun getCount(): Int
}

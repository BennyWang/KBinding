package com.benny.library.neobinding.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.benny.library.neobinding.bind.ItemViewModel
import com.benny.library.neobinding.view.IViewCreator

/**
 * Created by benny on 12/18/15.
 */

abstract class RecyclerAdapter<T> (val viewCreator: IViewCreator<T>, val itemAccessor: AdapterItemAccessor<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun notifyPropertyChange(data: T?, position: Int) {
            ((itemView.tag) as? ItemViewModel<T>)?.notifyPropertyChange(data, position)
        }
    }

    protected fun createViewHolder(itemView: View) : RecyclerView.ViewHolder {
        return ViewHolder<T>(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder<T>).notifyPropertyChange(itemAccessor.get(position), position)
    }

    override fun getItemCount(): Int {
        return itemAccessor.size()
    }
}
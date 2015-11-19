package com.benny.library.neobinding.kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.benny.library.neobinding.kotlin.bind.BindableModel

/**
 * Created by benny on 9/17/15.
 */

abstract class RecyclerPagingAdapter(internal var listener: AdapterPagingListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var hasNextPage = true

    protected inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun notifyPropertyChange(data: Any?, position: Int) {
            if (itemView.tag != null && itemView.tag is BindableModel<*>) {
                ((itemView.tag) as BindableModel<in Any?>).notifyPropertyChange(data, position)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).notifyPropertyChange(getItem(position), position)

        if (position == itemCount - 1 && hasNextPage) {
            listener.onLoadPage(getItem(position - 1), position)
        }
    }

    override fun getItemCount(): Int {
        return if (hasNextPage) countBase() + 1 else countBase()
    }

    fun loadComplete(hasNextPage: Boolean) {
        this.hasNextPage = hasNextPage
        notifyDataSetChanged()
    }

    abstract fun getItem(position: Int): Any?
    abstract fun countBase(): Int
}

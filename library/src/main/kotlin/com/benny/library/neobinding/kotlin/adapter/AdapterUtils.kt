package com.benny.library.neobinding.kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.benny.library.neobinding.bind.BindableModel
import com.benny.library.neobinding.kotlin.bind.IViewCreator

/**
 * Created by benny on 11/19/15.
 */

public object AdapterUtils {
    public fun <T> toAdapter(viewCreator: IViewCreator<T>, itemAccessor: AdapterItemAccessor<T>): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                fun notifyPropertyChange(data: T?, position: Int) {
                    ((itemView.tag) as? BindableModel<T>)?.notifyPropertyChange(data, position)
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view = viewCreator.view(parent.context, parent)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                (holder as ViewHolder).notifyPropertyChange(itemAccessor.get(position), position)
            }

            override fun getItemCount(): Int {
                return itemAccessor.size()
            }

            override fun getItemViewType(position: Int): Int {
                return viewCreator.viewTypeFor(itemAccessor.get(position), position)
            }
        };
    }

    public fun <T> toPagingAdapter(viewCreator: IViewCreator<T>, itemAccessor: AdapterItemAccessor<T>, listener: AdapterPagingListener<T>): RecyclerPagingAdapter<T> {
        return object : RecyclerPagingAdapter<T>(listener) {
            override fun getItem(position: Int): T? {
                return itemAccessor.get(position)
            }

            override fun countBase(): Int {
                return itemAccessor.size()
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
                val view = viewCreator.view(parent.context, parent)
                return createViewHolder(view)
            }

            override fun getItemViewType(position: Int): Int {
                return viewCreator.viewTypeFor(getItem(position), position)
            }
        }
    }
}
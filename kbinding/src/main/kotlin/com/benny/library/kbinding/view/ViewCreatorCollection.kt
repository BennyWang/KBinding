package com.benny.library.kbinding.view

import android.view.View
import android.view.ViewGroup
import rx.functions.Func2
import java.util.*

/**
 * Created by benny on 11/18/15.
 */

class ViewCreatorCollection<T> : IViewCreator<T> {

    private var viewTypeBegin = 0
    internal var lastViewType = -1

    private val viewTypeFilters = ArrayList<ViewTypeFilter<T>>()

    private fun filter(data: T?, position: Int): ViewTypeFilter<T> {
        for (viewTypeFilter in viewTypeFilters) {
            if (viewTypeFilter.canProcess(data, position)) return viewTypeFilter
        }
        throw RuntimeException("can not process view type for:" + data.toString())
    }

    override fun view(container: ViewGroup): View {
        val index = if (lastViewType != -1) lastViewType else viewTypeFilters.size - 1
        return viewTypeFilters[index].view(container)
    }

    override fun viewTypeFor(data: T?, position: Int): Int {
        lastViewType = filter(data, position).viewType
        return lastViewType
    }

    override fun viewTypeCount(): Int {
        return viewTypeFilters.size
    }

    fun add(filter: Func2<T, Int, Boolean>, viewCreator: ViewCreator): ViewCreatorCollection<T> {
        viewTypeFilters.add(ViewTypeFilter(filter, viewCreator, viewTypeBegin++))
        return this
    }

    private class ViewTypeFilter<T>(val filter: Func2<T, Int, Boolean>, val creator: ViewCreator, val viewType: Int) {
        fun canProcess(data: T?, position: Int): Boolean {
            return filter.call(data, position)
        }

        fun view(container: ViewGroup): View {
            return creator.view(container)
        }
    }
}
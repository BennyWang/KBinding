package com.benny.library.kbinding.view

import android.view.View
import android.view.ViewGroup
import rx.functions.Func3
import java.util.*

/**
 * Created by benny on 11/18/15.
 */

open class ViewCreatorCollection<T> : IViewCreator<T> {

    private var viewTypeBegin = 0
    internal var lastViewType = -1

    private val viewTypeFilters = ArrayList<ViewTypeFilter<T>>()

    private fun filter(data: T?, position: Int, itemCount: Int): ViewTypeFilter<T> {
        for (viewTypeFilter in viewTypeFilters) {
            if (viewTypeFilter.canProcess(data, position, itemCount)) return viewTypeFilter
        }
        throw RuntimeException("can not process view type for:" + data.toString())
    }

    override fun view(container: ViewGroup): View {
        val index = if (lastViewType != -1) lastViewType else viewTypeFilters.size - 1
        return viewTypeFilters[index].view(container)
    }

    override fun viewTypeFor(data: T?, position: Int, itemCount: Int): Int {
        lastViewType = filter(data, position, itemCount).viewType
        return lastViewType
    }

    override fun viewTypeCount(): Int {
        return viewTypeFilters.size
    }

    fun add(filter: Func3<T, Int, Int, Boolean>, viewCreator: ViewCreator): ViewCreatorCollection<T> {
        viewTypeFilters.add(ViewTypeFilter(filter, viewCreator, viewTypeBegin++))
        return this
    }

    private class ViewTypeFilter<T>(val filter: Func3<T, Int, Int, Boolean>, val creator: ViewCreator, val viewType: Int) {
        fun canProcess(data: T?, position: Int, itemCount: Int): Boolean {
            return filter.call(data, position, itemCount)
        }

        fun view(container: ViewGroup): View {
            return creator.view(container)
        }
    }
}
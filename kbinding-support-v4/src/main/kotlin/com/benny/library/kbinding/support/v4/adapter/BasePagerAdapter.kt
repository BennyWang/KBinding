package com.benny.library.kbinding.support.v4.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.benny.library.kbinding.bind.ItemViewModel
import com.benny.library.kbinding.view.IViewCreator

/**
 * Created by benny on 2/1/16.
 */

class BasePagerAdapter<T>(val viewCreator: IViewCreator<T>, val itemAccessor: PagerAdapterItemAccessor<T>) : PagerAdapter() {

    override fun isViewFromObject(p0: View?, p1: Any?): Boolean {
        return p0 == p1;
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any? {
        val itemView = viewCreator.view(container);
        container.addView(itemView)

        ((itemView.tag) as? ItemViewModel<T>)?.notifyPropertyChange(itemAccessor.get(position), position)

        return itemView
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return itemAccessor.getTitle(position)
    }

    override fun getCount(): Int {
        return itemAccessor.size()
    }
}
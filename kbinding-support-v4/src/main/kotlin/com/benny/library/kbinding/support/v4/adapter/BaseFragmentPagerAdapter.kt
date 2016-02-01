package com.benny.library.kbinding.support.v4.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by benny on 2/1/16.
 */

class BaseFragmentPagerAdapter(val fragmentManager: FragmentManager, val itemAccessor: PagerAdapterItemAccessor<Fragment>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(p0: Int): Fragment? {
        return itemAccessor.get(p0)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return itemAccessor.getTitle(position)
    }

    override fun getCount(): Int {
        return itemAccessor.size()
    }
}
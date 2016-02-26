package com.benny.library.kbinding.support.v4.converter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import com.benny.library.kbinding.converter.OneWayConverter
import com.benny.library.kbinding.support.v4.adapter.BaseFragmentPagerAdapter
import com.benny.library.kbinding.support.v4.adapter.BasePagerAdapter
import com.benny.library.kbinding.support.v4.adapter.PagerAdapterItemAccessor
import com.benny.library.kbinding.support.v4.adapter.SimplePagerAdapterItemAccessor
import com.benny.library.kbinding.view.IViewCreator

/**
 * Created by benny on 2/1/16.
 */

class ListToPagerAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: (List<T>) -> PagerAdapterItemAccessor<T> = { list -> SimplePagerAdapterItemAccessor(list) } ) : OneWayConverter<List<T>, PagerAdapter> {
    override fun convert(source: List<T>): PagerAdapter {
        return BasePagerAdapter(viewCreator, itemAccessorFactory(source))
    }
}

class ListToFragmentPagerAdapterConverter(val FragmentManager: FragmentManager, val itemAccessorFactory: (List<Fragment>) -> PagerAdapterItemAccessor<Fragment> = { list -> SimplePagerAdapterItemAccessor(list) } ) : OneWayConverter<List<Fragment>, FragmentPagerAdapter> {
    override fun convert(source: List<Fragment>): FragmentPagerAdapter {
        return BaseFragmentPagerAdapter(FragmentManager, itemAccessorFactory(source))
    }
}
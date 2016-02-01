package com.benny.library.kbinding.support.v4.converter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.util.Log
import com.benny.library.kbinding.converter.OneWayConverter
import com.benny.library.kbinding.support.v4.adapter.*
import com.benny.library.kbinding.view.IViewCreator

/**
 * Created by benny on 2/1/16.
 */

class ListToPagerAdapterConverter<T>(val viewCreator: IViewCreator<T>, val itemAccessorFactory: (List<T>) -> PagerAdapterItemAccessor<T> = { list -> SimplePagerAdapterItemAccessor(list) } ) : OneWayConverter<PagerAdapter> {
    override fun convert(source: Any): PagerAdapter {
        return BasePagerAdapter(viewCreator, itemAccessorFactory(source as List<T>))
    }
}

class ListToFragmentPagerAdapterConverter(val FragmentManager: FragmentManager, val itemAccessorFactory: (List<Fragment>) -> PagerAdapterItemAccessor<Fragment> = { list -> SimplePagerAdapterItemAccessor(list) } ) : OneWayConverter<FragmentPagerAdapter> {
    override fun convert(source: Any): FragmentPagerAdapter {
        val fpa = BaseFragmentPagerAdapter(FragmentManager, itemAccessorFactory(source as List<Fragment>))
        Log.d("FragmentPagerAdapter", "convert is " + fpa);
        return fpa
    }
}
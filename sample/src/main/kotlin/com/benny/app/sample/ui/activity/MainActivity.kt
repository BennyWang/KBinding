package com.benny.app.sample.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.benny.app.sample.SampleApplication

import org.jetbrains.anko.support.v4.viewPager
import org.jetbrains.anko.*

import com.benny.library.neobinding.view.*
import com.benny.app.sample.extension.generateViewId
import com.benny.app.sample.ui.extension.viewPagerIndicator
import com.benny.app.sample.ui.fragment.LoginFragment
import com.benny.app.sample.ui.fragment.SampleFragment
import com.benny.app.sample.ui.fragment.StockFragment
import com.benny.app.sample.ui.widget.ViewPagerIndicator


class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MainActivityUI().setContentView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        SampleApplication.getRefWatcher(this).watch(this)
    }

    class MainFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment? {
            return when(position) {
                0 -> LoginFragment()
                1 -> StockFragment()
                else -> SampleFragment()
            }
        }

        override fun getCount(): Int {
            return 4;
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return "sample $position"
        }
    }

    class MainActivityUI : ViewBinderComponent<MainActivity> {
        override fun builder(): AnkoContext<MainActivity>.() -> Unit = {
            verticalLayout {
                val pager = viewPager {
                    id = generateViewId()
                    adapter = MainFragmentPagerAdapter(owner.supportFragmentManager)
                }.lparams(matchParent, 0, 1f)

                viewPagerIndicator {
                    indicatorLinePosition = ViewPagerIndicator.TOP
                    indicatorLineMargin = dip(5)
                    viewPager = pager
                }.lparams(matchParent, wrapContent)
            }
        }
    }
}

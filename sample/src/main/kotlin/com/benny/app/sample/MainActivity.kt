package com.benny.app.sample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.benny.app.sample.extension.generateViewId
import com.benny.app.sample.ui.extension.viewPagerIndicator
import com.benny.app.sample.ui.fragment.LoginFragment
import com.benny.app.sample.ui.fragment.SampleFragment
import com.benny.app.sample.ui.fragment.StockFragment
import com.benny.app.sample.ui.widget.ViewPagerIndicator
import com.trello.rxlifecycle.components.support.RxFragmentActivity
import org.jetbrains.anko.*

import com.benny.library.neobinding.view.*

import org.jetbrains.anko.support.v4.viewPager

class MainActivity : RxFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApplicationContext.init(this)

        MainActivityUI().setContentView(this)
    }

    inner class MainFragmentPagerAdapter : FragmentPagerAdapter(supportFragmentManager) {

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

    inner class MainActivityUI : ViewBinderComponent<MainActivity> {
        override fun builder(): AnkoContext<*>.() -> Unit = {
            verticalLayout {
                val pager = viewPager {
                    id = generateViewId()
                    adapter = MainFragmentPagerAdapter()
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

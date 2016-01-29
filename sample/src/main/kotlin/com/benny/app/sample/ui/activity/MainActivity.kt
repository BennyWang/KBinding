package com.benny.app.sample.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import com.benny.app.sample.R
import com.benny.app.sample.SampleApplication

import org.jetbrains.anko.support.v4.viewPager
import org.jetbrains.anko.*

import com.benny.app.sample.extension.generateViewId
import com.benny.app.sample.ui.extension.viewPagerIndicator
import com.benny.app.sample.ui.fragment.LoginFragment
import com.benny.app.sample.ui.fragment.MovieListFragment
import com.benny.app.sample.ui.fragment.SampleFragment
import com.benny.app.sample.ui.fragment.StockFragment
import com.benny.app.sample.ui.widget.ViewPagerIndicator
import com.benny.app.sample.viewcomponent.TitleToolBarViewComponent
import com.benny.library.kbinding.dsl.inflate
import com.benny.library.kbinding.view.ViewBinderComponent
import org.jetbrains.anko.design.appBarLayout


class MainActivity : BaseActivity() {
    lateinit var toolBar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("MainActivity", "onCreate this:" + this)

        setTheme(R.style.AppTheme)
        MainActivityUI().setContentView(this)
        setSupportActionBar(toolBar)
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.title = "";
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    class MainFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment? {
            return when(position) {
                0 -> LoginFragment()
                1 -> MovieListFragment()
                2 -> StockFragment()
                else -> SampleFragment()
            }
        }

        override fun getCount(): Int {
            return 4;
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position) {
                0 -> "登录"
                1 -> "豆瓣"
                2 -> "股票"
                else -> "预留"
            }
        }
    }

    inner class MainActivityUI : ViewBinderComponent<MainActivity> {
        override fun builder(): AnkoContext<MainActivity>.() -> Unit = {
            verticalLayout {
                appBarLayout {
                    toolBar = inflate(TitleToolBarViewComponent("主页"), this@appBarLayout) as Toolbar
                }
                val pager = viewPager {
                    id = generateViewId()
                    adapter = MainFragmentPagerAdapter(owner.supportFragmentManager)
                }.lparams(matchParent, 0, 1f)

                viewPagerIndicator {
                    backgroundColor = Color.parseColor("#393a4c")
                    indicatorLinePosition = ViewPagerIndicator.TOP
                    indicatorLineMargin = dip(5)
                    indicatorLineHeight = dip(4)
                    viewPager = pager
                }.lparams(matchParent, wrapContent)
            }
        }
    }
}

package com.benny.app.sample.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu

import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.support.v4.viewPager

import com.benny.library.kbinding.dsl.inflate
import com.benny.library.kbinding.view.ViewBinderComponent

import com.benny.app.sample.R
import com.benny.app.sample.utils.generateViewId
import com.benny.app.sample.ui.extension.viewPagerIndicator
import com.benny.app.sample.ui.fragment.LoginFragment
import com.benny.app.sample.ui.fragment.MovieListFragment
import com.benny.app.sample.ui.fragment.SampleFragment
import com.benny.app.sample.ui.fragment.StockFragment
import com.benny.app.sample.ui.widget.ViewPagerIndicator
import com.benny.app.sample.ui.layout.TitleToolBarView
import com.benny.library.kbinding.bind.BindingDelegate
import com.benny.library.kbinding.dsl.bind
import com.benny.library.kbinding.support.v4.adapter.SimplePagerAdapterItemAccessor
import com.benny.library.kbinding.support.v4.bindings.fragmentAdapter
import com.benny.library.kbinding.support.v4.converter.ListToFragmentPagerAdapterConverter
import com.benny.library.kbinding.view.setContentView
import org.jetbrains.anko.*


class MainActivity : BaseActivity() {
    lateinit var toolBar: Toolbar
    val bindingDelegate = BindingDelegate()

    val fragments: List<Fragment> by bindingDelegate.bindProperty("fragments") { listOf(LoginFragment(), MovieListFragment(), StockFragment(), SampleFragment()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("MainActivity", "onCreate this:" + this)

        setTheme(R.style.AppTheme)
        MainActivityUI().setContentView(this).bindTo(bindingDelegate)

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar?.title = "";
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    class PagerAdapterAccessor(list: List<Fragment>) : SimplePagerAdapterItemAccessor<Fragment>(list) {
        override fun getTitle(position: Int): String = when(position) {
            0 -> "登录"
            1 -> "豆瓣"
            2 -> "股票"
            else -> "预留"
        }
    }

    inner class MainActivityUI : ViewBinderComponent<MainActivity> {
        override fun builder(): AnkoContext<*>.() -> Unit = {
            verticalLayout {
                appBarLayout {
                    toolBar = inflate(TitleToolBarView("主页"), this@appBarLayout) as Toolbar
                }
                val pager = viewPager {
                    id = generateViewId()
                    bind { fragmentAdapter("fragments", converter = ListToFragmentPagerAdapterConverter((owner as MainActivity).supportFragmentManager, { PagerAdapterAccessor(it) })) }
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

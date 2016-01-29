package com.benny.app.sample.ui.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import org.jetbrains.anko.*

import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * Created by Benny on 2015/7/13.
 */

class ViewPagerIndicator(context: Context) : _LinearLayout(context), ViewPager.OnPageChangeListener {
    private val DEFAULT_INDICATOR = object : IndicatorFactory {
        override fun create(title: CharSequence, `object`: Any?, container: ViewGroup): View = container.context.UI {
            textView {
                verticalPadding = dip(14)
                horizontalPadding = 0
                text = title
                textColor = Color.WHITE
                this.gravity = Gravity.CENTER
            }
        }.view
    }

    lateinit var indicatorLine: View
    lateinit var tabsContainer: LinearLayout

    var viewPager: ViewPager? = null
        set(value) {
            if(value == null) return
            field = value
            try {
                value.addOnPageChangeListener(this)
                setOnAdapterChangeListenerMethod.invoke(value, Proxy.newProxyInstance(onAdapterChangeListenerInterface.classLoader, arrayOf(onAdapterChangeListenerInterface), ProxyListener()))
            } catch (ignored: Exception) {
            }

            if (value.adapter != null) setupView()
        }

    var divideEqually = true

    var showIndicatorLine = true
    var indicatorLineHeight = 2
    var indicatorLinePosition = BOTTOM
    var indicatorLineMargin = 0

    var indicatorBackgroundColor: Int = Color.WHITE
    var indicatorBackgroundDrawable: Drawable? = null

    var indicatorFactory = DEFAULT_INDICATOR

    fun ensureLayoutInitialized() {
        if(childCount != 0) return

        val indicatorLineFactory: _LinearLayout.() -> Unit = {
            indicatorLine = view {
                if (indicatorBackgroundDrawable != null) backgroundDrawable = indicatorBackgroundDrawable!!
                else backgroundColor = indicatorBackgroundColor

                visibility = if(showIndicatorLine) VISIBLE else GONE
            }.lparams(0, indicatorLineHeight) {
                horizontalMargin = indicatorLineMargin
            }
        }

        val tabsContainerFactory: _LinearLayout.() -> Unit = {
            tabsContainer = linearLayout().lparams(matchParent, 0, 1f)
        }

        orientation = VERTICAL

        if(indicatorLinePosition == TOP) {
            indicatorLineFactory()
            tabsContainerFactory()
        }
        else {
            tabsContainerFactory()
            indicatorLineFactory()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (tabsContainer.childCount > 0 && showIndicatorLine) {
            indicatorLine.layoutParams.width = tabsContainer.getChildAt(0).measuredWidth - 2*indicatorLineMargin
            indicatorLine.translationX = 0f
        }
    }

    private fun setupView() {
        ensureLayoutInitialized()

        val adapter = viewPager!!.adapter
        val indicatorAdapter = wrapIndicatorAdapter(adapter)
        tabsContainer.removeAllViews()

        if (adapter.count <= 1) return

        for (i in 0..adapter.count - 1) {
            val view = indicatorFactory.create(adapter.getPageTitle(i), indicatorAdapter.getIndicatorData(i), tabsContainer)
            tabsContainer.addView(view)

            val currentIndex = i
            view.setOnClickListener { setCurrentItem(currentIndex) }
            view.isSelected = (i == 0)

            if (divideEqually) {
                val params = view.layoutParams as LinearLayout.LayoutParams
                params.width = 0
                params.weight = 1f
                view.layoutParams = params
            }
        }
    }

    internal fun onAdapterChanged(old: PagerAdapter, adapter: PagerAdapter) {
        setupView()
    }

    internal fun setCurrentItem(item: Int) {
        for (i in 0..childCount - 1) {
            getChildAt(i).isSelected = i == item
        }

        if (viewPager?.currentItem != item) viewPager?.setCurrentItem(item, true)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if(showIndicatorLine) indicatorLine.translationX = (indicatorLine.width + 2*indicatorLineMargin) * (position + positionOffset)
    }

    override fun onPageSelected(position: Int) {
        setCurrentItem(position)


    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    interface IndicatorFactory {
        fun create(title: CharSequence, `object`: Any?, container: ViewGroup): View
    }

    interface IndicatorAdapter {
        fun getIndicatorData(position: Int): Any?
    }

    private inner class ProxyListener : java.lang.reflect.InvocationHandler {
        @Throws(Throwable::class)
        override fun invoke(proxy: Any, m: Method, args: Array<Any>): Any? {
            try {
                if (m.name == "onAdapterChanged") {
                    onAdapterChanged(args[0] as PagerAdapter, args[1] as PagerAdapter)
                }
            } catch (e: Exception) {
                throw RuntimeException("unexpected invocation exception: " + e.message)
            }

            return null
        }
    }

    companion object {
        val TOP = 0
        val BOTTOM = 1

        private lateinit var setOnAdapterChangeListenerMethod: Method
        private lateinit var onAdapterChangeListenerInterface: Class<*>

        init {
            try {
                onAdapterChangeListenerInterface = Class.forName("android.support.v4.view.ViewPager\$OnAdapterChangeListener")
                setOnAdapterChangeListenerMethod = ViewPager::class.java.getDeclaredMethod("setOnAdapterChangeListener", onAdapterChangeListenerInterface)
                setOnAdapterChangeListenerMethod.isAccessible = true
            } catch (ignored: Exception) {
            }

        }

        private fun wrapIndicatorAdapter(adapter: PagerAdapter): IndicatorAdapter {
            if (adapter is IndicatorAdapter) return adapter

            return object : IndicatorAdapter {
                override fun getIndicatorData(position: Int): Any? {
                    return null
                }
            }
        }
    }
}

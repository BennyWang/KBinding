package com.benny.app.sample.ui.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import com.ToxicBakery.viewpager.transforms.ABaseTransformer
import com.benny.app.sample.R
import com.benny.library.autoadapter.AutoPagerAdapter
import com.benny.library.autoadapter.factory.Factory
import com.benny.library.autoadapter.viewcreator.ViewCreator
import com.benny.library.autoadapter.viewholder.DataGetter
import com.benny.library.autoadapter.viewholder.IViewHolder
import com.benny.library.kbinding.view.ViewBinderComponent
import com.benny.library.kbinding.view.setContentView
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.viewPager
import kotlin.properties.Delegates

/**
 * Created by benny on 8/17/16.
 */

class ViewPagerTransformerActivity : BaseActivity() {
    val MENU_ITEMS: List<Int> = listOf(R.mipmap.games, R.mipmap.earth, R.mipmap.buddies,
            R.mipmap.camera3, R.mipmap.secret, R.mipmap.set, R.mipmap.moment, R.mipmap.games,
            R.mipmap.earth, R.mipmap.buddies, R.mipmap.camera3,R.mipmap.secret, R.mipmap.set)

    var vPager: ViewPager by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewPagerTransformerActivityUI().setContentView(this)

        initView()
    }

    fun initView() {
        vPager.adapter = AutoPagerAdapter(MENU_ITEMS, ViewCreator(R.layout.adapter_item_image, Factory { IconViewHolder() }))
        vPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            var lastPosition = 0

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    lastPosition = vPager.currentItem
                } else if (state == ViewPager.SCROLL_STATE_IDLE && lastPosition != vPager.currentItem) {
                    val position = vPager.currentItem
                    if (position <= 2) {
                        vPager.adapter = vPager.adapter
                        vPager.setCurrentItem(MENU_ITEMS.size - 6 + position, false)//6
                    } else if (position >= MENU_ITEMS.size - 3) {//3
                        vPager.adapter = vPager.adapter
                        vPager.setCurrentItem(3 + (position - MENU_ITEMS.size + 3), false)//3
                    }
                }
            }

            override fun onPageSelected(position: Int) {
            }
        })
        vPager.setCurrentItem(5, false)
    }

    inner class IconViewHolder : IViewHolder<Int> {
        var vIcon: ImageView by Delegates.notNull()
        override fun bind(view: View) {
            vIcon = view.find(R.id.icon)
        }

        override fun onDataChange(getter: DataGetter<Int>, position: Int) {
            vIcon.setImageResource(getter.data)
        }
    }

    class ViewPagerTransformerActivityUI : ViewBinderComponent<ViewPagerTransformerActivity> {
        override fun builder(): AnkoContext<out ViewPagerTransformerActivity>.() -> Unit = {
            relativeLayout {
                owner.vPager = viewPager {
                    pageMargin = -dip(600)
                    clipToPadding = false
                    setPageTransformer(false, MainMenuTransformer())
                    offscreenPageLimit = owner.MENU_ITEMS.size / 2 - 1
                }.lparams(width = matchParent, height = dip(300f)) {
                    centerVertically()
                }
            }
        }
    }

    class MainMenuTransformer : ABaseTransformer() {
        private val MIN_SCALE = 0.5f
        private val MIN_ALPHA = 0.5f

        private var minStep: Float? = null

        override fun onTransform(page: View, position: Float) {
            if(minStep == null) {
                if(position != 0f) minStep = Math.abs(position)
            }

            val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))

            val pageWidth = page.width
            val pageHeight = page.height

            page.pivotX = 0.5f * pageWidth
            page.pivotY = 0.5f * pageHeight

            val icon = page.findViewById(R.id.icon)

            if (position < 0) {
                page.pivotX = icon.right.toFloat()
                page.translationX = Math.max(Math.abs(position) - minStep!!, 0f) * icon.width
            } else if (position > 0) {
                page.pivotX = icon.left.toFloat()
                page.translationX = -Math.max(Math.abs(position) - minStep!!, 0f) * icon.width
            }

            page.scaleX = scaleFactor
            page.scaleY = scaleFactor

            page.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)

            icon.isClickable = position == 0f
        }
    }
}
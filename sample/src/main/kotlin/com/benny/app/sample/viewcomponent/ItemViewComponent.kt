package com.benny.app.sample.viewcomponent

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import com.benny.app.sample.Constants
import com.benny.app.sample.R
import com.benny.app.sample.converter.StockColorConverter
import com.benny.app.sample.converter.StockPriceChangePercentageConverter
import com.benny.app.sample.converter.StockPriceConverter
import com.benny.app.sample.extension.generateViewId
import com.benny.app.sample.model.Stock
import com.benny.library.kbinding.converter.MultipleConverter
import com.benny.library.kbinding.drawable.roundRect
import com.benny.library.kbinding.extension.*
import org.jetbrains.anko.*

import com.benny.library.kbinding.view.ViewBinderComponent

/**
 * Created by benny on 12/16/15.
 */

class LoadingItemView : ViewBinderComponent<Any> {
    override fun builder(): AnkoContext<Any>.() -> Unit = {
        frameLayout {
            progressBar (android.R.attr.progressBarStyle) {
                isIndeterminate = true
            }.lparams(width = dip(24), height = dip(24)) { gravity = Gravity.CENTER }
        }.layoutParams = ViewGroup.LayoutParams(matchParent, dip(50))
    }
}

class StockItemView : ViewBinderComponent<Any> {
    class ChangePercentBackgroundConverter(val context: Context) : MultipleConverter<Drawable> {
        override fun convert(params: Array<Any>): Drawable {
            if(params.size < 2) return context.roundRect{ radius = context.dip(2).toFloat(); color = context.resources.getColor(R.color.color_9) }
            val listedState = params[0] as? Int ?: Stock.LISTED_STATE_ABNORMAL
            val changePercent = params[1] as? Float ?: 0f

            if(listedState == Stock.LISTED_STATE_NORMAL) {
                return context.roundRect{ radius = context.dip(2).toFloat(); color = StockColorConverter(context.resources.getColor(R.color.color_9)).convert(changePercent) }
            }

            return context.roundRect{ radius = context.dip(2).toFloat(); color = context.resources.getColor(R.color.color_9) }
        }
    }

    class ChangePercentConverter : MultipleConverter<String> {
        override fun convert(params: Array<Any>): String {
            if(params.size < 2) return "+0.00%"
            val listedState = params[0] as? Int ?: Stock.LISTED_STATE_ABNORMAL
            val changePercent = params[1] as? Float ?: 0f

            if(listedState == Stock.LISTED_STATE_NORMAL) return StockPriceChangePercentageConverter().convert(changePercent)

            return Stock.getListedStateDescription(listedState)
        }
    }

    override fun builder(): AnkoContext<Any>.() -> Unit = {
            relativeLayout {
                verticalLayout {
                    verticalPadding = dip(7)
                    textView {
                        textSize = 16f
                        textColorResource = R.color.color_3
                        singleLine = true
                        maxEms = 8
                        ellipsize = TextUtils.TruncateAt.END
                        bind { text(path = "name") }
                    }.lparams(width = wrapContent)
                    textView {
                        textSize = 9f
                        textColorResource = R.color.color_9
                        bind { text(path = "symbol") }
                    }.lparams(width = wrapContent)
                }.lparams { leftMargin = dip(14) }
                val v = textView {
                    id = generateViewId()
                    textSize = 16f
                    textColor = Color.WHITE
                    textStyle = Typeface.BOLD
                    this@textView.gravity = Gravity.CENTER
                    bind { background(paths = listOf("listedState", "changePercent"), converter = ChangePercentBackgroundConverter(ctx)) }
                    bind { text(paths = listOf("listedState", "changePercent"), converter = ChangePercentConverter()) }
                }.lparams(width = dip(82), height = dip(33)) {
                    alignParentRight()
                    centerVertically()
                    rightMargin = dip(14)
                }
                textView {
                    textSize = 16f
                    textColorResource = R.color.color_3
                    this@textView.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
                    bind { text(path = "price", converter = StockPriceConverter()) }
                }.lparams {
                    leftOf(v)
                    centerVertically()
                    rightMargin = dip(14)
                }
            }
    }
}




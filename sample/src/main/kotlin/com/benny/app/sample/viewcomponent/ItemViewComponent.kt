package com.benny.app.sample.viewcomponent

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import com.benny.app.sample.R
import com.benny.app.sample.converter.StockColorConverter
import com.benny.app.sample.converter.StockPriceChangePercentageConverter
import com.benny.app.sample.converter.StockPriceConverter
import com.benny.app.sample.extension.generateViewId
import com.benny.app.sample.network.service.caishuo.model.Stock
import com.benny.app.sample.ui.extension.simpleDraweeView
import com.benny.library.common.bindings.*
import com.benny.library.common.roundRect
import com.benny.library.kbinding.converter.MultipleConverter
import com.benny.library.kbinding.converter.StringConverter
import com.benny.library.kbinding.dsl.*
import com.benny.library.kbinding.view.ItemViewBinderComponent
import org.jetbrains.anko.*

import com.benny.library.kbinding.view.ViewBinderComponent

/**
 * Created by benny on 12/16/15.
 */

class LoadingItemView : ItemViewBinderComponent {
    override fun builder(): AnkoContext<*>.() -> Unit = {
        frameLayout {
            progressBar () {
                isIndeterminate = true
            }.lparams(width = dip(24), height = dip(24)) { gravity = Gravity.CENTER }
        }.layoutParams = ViewGroup.LayoutParams(matchParent, dip(50))
    }
}

class MovieItemView : ItemViewBinderComponent {
    override fun builder(): AnkoContext<*>.() -> Unit = {
        relativeLayout {
            linearLayout {
                padding = dip(14)
                simpleDraweeView {
                    bind { src("smallCover", mode = OneWay) }
                }.lparams(dip(65), dip(100))
                verticalLayout {
                    textView {
                        textSize = 16f
                        textWeight = Typeface.BOLD
                        bind { text("title", mode = OneWay) }
                    }.lparams { bottomMargin = dip(8) }
                    linearLayout {
                        textView { text = "评分: " }
                        textView {
                            bind { text("score", mode = OneWay, converter = StringConverter()) }
                        }
                    }.lparams { bottomMargin = dip(8) }
                    linearLayout {
                        textView { text = "演员: " }
                        textView {
                            singleLine = true
                            ellipsize = TextUtils.TruncateAt.END
                            bind { text("casts", mode = OneWay) }
                        }
                    }
                }.lparams(matchParent, wrapContent) {
                    this@lparams.gravity = Gravity.CENTER_VERTICAL
                    leftMargin = dip(14)
                }
            }.lparams(matchParent, wrapContent)
        }
    }
}

class StockItemView : ItemViewBinderComponent {
    class ChangePercentBackgroundConverter(val context: Context) : MultipleConverter<Drawable> {
        override fun convert(params: Array<Any>): Drawable {
            if(params.size < 2) return roundRect { radius = context.dip(2).toFloat(); color = context.resources.getColor(R.color.color_9) }
            val listedState = params[0] as? Int ?: Stock.LISTED_STATE_ABNORMAL
            val changePercent = params[1] as? Float ?: 0f

            if(listedState == Stock.LISTED_STATE_NORMAL) {
                return roundRect { radius = context.dip(2).toFloat(); color = StockColorConverter(context.resources.getColor(R.color.color_9)).convert(changePercent) }
            }

            return roundRect { radius = context.dip(2).toFloat(); color = context.resources.getColor(R.color.color_9) }
        }
    }

    class ChangePercentConverter : MultipleConverter<CharSequence> {
        override fun convert(params: Array<Any>): CharSequence {
            if(params.size < 2) return "+0.00%"
            val listedState = params[0] as? Int ?: Stock.LISTED_STATE_ABNORMAL
            val changePercent = params[1] as? Float ?: 0f

            if(listedState == Stock.LISTED_STATE_NORMAL) return StockPriceChangePercentageConverter().convert(changePercent)

            return Stock.getListedStateDescription(listedState)
        }
    }

    override fun builder(): AnkoContext<*>.() -> Unit = {
            relativeLayout {
                verticalLayout {
                    verticalPadding = dip(7)
                    textView {
                        textSize = 16f
                        textColorResource = R.color.color_3
                        singleLine = true
                        maxEms = 8
                        ellipsize = TextUtils.TruncateAt.END
                        bind { text("name", mode = OneWay) }
                    }.lparams(width = wrapContent)
                    textView {
                        textSize = 9f
                        textColorResource = R.color.color_9
                        bind { text("symbol", mode = OneWay) }
                    }.lparams(width = wrapContent)
                }.lparams { leftMargin = dip(14) }
                val v = textView {
                    id = generateViewId()
                    textSize = 16f
                    textColor = Color.WHITE
                    textWeight = Typeface.BOLD
                    this@textView.gravity = Gravity.CENTER
                    bind { background("listedState", "changePercent", converter = ChangePercentBackgroundConverter(ctx)) }
                    bind { text("listedState", "changePercent", converter = ChangePercentConverter()) }
                }.lparams(width = dip(82), height = dip(33)) {
                    alignParentRight()
                    centerVertically()
                    rightMargin = dip(14)
                }
                textView {
                    textSize = 16f
                    textColorResource = R.color.color_3
                    this@textView.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
                    bind { text("price", mode = OneWay, converter = StockPriceConverter()) }
                }.lparams {
                    leftOf(v)
                    centerVertically()
                    rightMargin = dip(14)
                }
            }
    }
}




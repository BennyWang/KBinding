package com.benny.app.sample.ui.layout.stock

import android.graphics.Color
import android.support.v7.widget.GridLayout
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.benny.app.sample.converter.StockColorConverter
import com.benny.app.sample.converter.StockPriceChangeConverter
import com.benny.app.sample.converter.StockPriceChangePercentageConverter
import com.benny.app.sample.converter.StockPriceConverter
import com.benny.app.sample.network.service.caishuo.model.MarketType
import com.benny.library.kbinding.common.bindings.text
import com.benny.library.kbinding.common.bindings.textColor
import com.benny.library.kbinding.common.bindings.until
import com.benny.library.kbinding.common.style
import com.benny.library.kbinding.common.viewStyle
import com.benny.library.kbinding.converter.EmptyOneWayConverter
import com.benny.library.kbinding.converter.OneWayConverter
import com.benny.library.kbinding.dsl.OneWay
import com.benny.library.kbinding.dsl.bind
import com.benny.library.kbinding.dsl.inflate
import com.benny.library.kbinding.dsl.wait
import com.benny.library.kbinding.view.ViewBinderComponent
import org.jetbrains.anko.*
import org.jetbrains.anko.gridlayout.v7.gridLayout
import org.jetbrains.anko.gridlayout.v7.space

/**
 * Created by benny on 12/31/15.
 */

abstract class StockSubInfoUI : ViewBinderComponent<View> {
    fun ViewGroup.cell(ankoContext: AnkoContext<*>, title: String, key: String, converter: OneWayConverter<CharSequence> = EmptyOneWayConverter()): View = with(ankoContext) {
        this@cell.linearLayout {
            textView {
                textSize = 12f
                textColor = Color.parseColor("#999999")
                text = title
            }
            textView {
                text = "--"
                textSize = 12f
                textColor = Color.WHITE
                gravity = Gravity.RIGHT
                bind{ text(key, mode = OneWay, converter = converter) }
            }.lparams(matchParent)
        }
    }
}

class StockSubInfoHSUI : StockSubInfoUI() {
    override fun builder(): AnkoContext<*>.() -> Unit = {
        val ankoContext = this
        gridLayout {
            cell(ankoContext, "最高", "high").lparams(GridLayout.spec(0, GridLayout.TOP), GridLayout.spec(0, GridLayout.FILL, 1f)) { rightMargin = dip(5)}
            cell(ankoContext, "最低", "high").lparams(GridLayout.spec(0, GridLayout.TOP), GridLayout.spec(1, GridLayout.FILL, 1f)) { rightMargin = dip(5)}
            cell(ankoContext, "成交额", "high").lparams(GridLayout.spec(0, GridLayout.TOP), GridLayout.spec(2, GridLayout.FILL, 1f))

            space().lparams(GridLayout.spec(1, GridLayout.CENTER), GridLayout.spec(0, GridLayout.FILL, 1f)) { topMargin = dip(3); height = 0 }

            cell(ankoContext, "市盈率", "high").lparams(GridLayout.spec(2, GridLayout.TOP), GridLayout.spec(0, GridLayout.FILL, 1f)) { rightMargin = dip(5)}
            cell(ankoContext, "市净率", "high").lparams(GridLayout.spec(2, GridLayout.TOP), GridLayout.spec(1, GridLayout.FILL, 1f)) { rightMargin = dip(5)}
            cell(ankoContext, "总市值", "high").lparams(GridLayout.spec(2, GridLayout.TOP), GridLayout.spec(2, GridLayout.FILL, 1f))

            space().lparams(GridLayout.spec(3, GridLayout.CENTER), GridLayout.spec(0, GridLayout.FILL, 1f)) { topMargin = dip(3); height = 0 }

            cell(ankoContext, "振幅", "high").lparams(GridLayout.spec(4, GridLayout.TOP), GridLayout.spec(0, GridLayout.FILL, 1f)) { rightMargin = dip(5)}
            cell(ankoContext, "每股收益", "high").lparams(GridLayout.spec(4, GridLayout.TOP), GridLayout.spec(1, GridLayout.FILL, 1f)) { rightMargin = dip(5)}
            cell(ankoContext, "流通市值", "high").lparams(GridLayout.spec(4, GridLayout.TOP), GridLayout.spec(2, GridLayout.FILL, 1f))
        }
    }
}

class StockSubInfoHKUI : StockSubInfoUI() {
    override fun builder(): AnkoContext<*>.() -> Unit = {
        val ankoContext = this
        gridLayout {
            cell(ankoContext, "最高", "high").lparams(GridLayout.spec(0, GridLayout.TOP), GridLayout.spec(0, GridLayout.FILL, 1f)) { rightMargin = dip(5)}
            cell(ankoContext, "最低", "high").lparams(GridLayout.spec(0, GridLayout.TOP), GridLayout.spec(1, GridLayout.FILL, 1f)) { rightMargin = dip(5)}
            cell(ankoContext, "成交额", "high").lparams(GridLayout.spec(0, GridLayout.TOP), GridLayout.spec(2, GridLayout.FILL, 1f))

            space().lparams(GridLayout.spec(1, GridLayout.CENTER), GridLayout.spec(0, GridLayout.FILL, 1f)) { topMargin = dip(3); height = 0 }

            cell(ankoContext, "52周高", "high").lparams(GridLayout.spec(2, GridLayout.TOP), GridLayout.spec(0, GridLayout.FILL, 1f)) { rightMargin = dip(5)}
            cell(ankoContext, "52周低", "high").lparams(GridLayout.spec(2, GridLayout.TOP), GridLayout.spec(1, GridLayout.FILL, 1f)) { rightMargin = dip(5)}
            cell(ankoContext, "股息", "high").lparams(GridLayout.spec(2, GridLayout.TOP), GridLayout.spec(2, GridLayout.FILL, 1f))
        }
    }
}


class StockInfoUI : ViewBinderComponent<ViewGroup> {
    val visibility4HS = object : OneWayConverter<Boolean> {
        override fun convert(source: Any?): Boolean {
            return true
        }
    }

    val viewOfMarket = object : OneWayConverter<ViewBinderComponent<*>> {
        override fun convert(source: Any?): ViewBinderComponent<*> {
            if(source !is MarketType) return StockSubInfoHSUI()

            return if(source == MarketType.SH_SZ) StockSubInfoHSUI() else StockSubInfoHKUI()
        }
    }

    override fun builder(): AnkoContext<*>.() -> Unit = {
        fun ViewGroup.cell(title: String, key: String): View = with(this) {
            verticalLayout {
                textView {
                    textSize = 12f
                    textColor = Color.parseColor("#999999")
                    text = title
                }
                textView {
                    text = "--"
                    textSize = 12f
                    textColor = Color.WHITE
                }
            }
        }

        verticalLayout {
            horizontalPadding = dip(12)
            bottomPadding = dip(12)
            backgroundColor = Color.parseColor("#393a4c")
            linearLayout {
                verticalLayout {
                    textView {
                        textSize = 42f
                        textColor = Color.WHITE
                        gravity = Gravity.CENTER
                        bind { text("price", mode=OneWay, converter = StockPriceConverter()) }
                        bind { textColor("changePrice", mode=OneWay, converter = StockColorConverter()) }
                        text = "--"
                    }
                    linearLayout {
                        val tvStyle = viewStyle<TextView> {
                            textSize = 20f
                            textColor = Color.WHITE
                            gravity = Gravity.CENTER_HORIZONTAL
                        }
                        textView {
                            style = tvStyle
                            bind { text("changePrice", mode=OneWay, converter = StockPriceChangeConverter()) }
                            bind { textColor("changePrice", mode=OneWay, converter = StockColorConverter()) }
                            text = "--"
                        }.lparams(0, wrapContent, 1f)
                        textView {
                            style = tvStyle
                            bind { text("changePercent", mode=OneWay, converter = StockPriceChangePercentageConverter()) }
                            bind { textColor("changePrice", mode=OneWay, converter = StockColorConverter()) }
                            text = "--"
                        }.lparams(0, wrapContent, 1f)
                    }
                }.lparams(weight = 0.6f)

                gridLayout {
                    cell("今开", "open").lparams(GridLayout.spec(0, GridLayout.CENTER), GridLayout.spec(0, GridLayout.LEFT, 1f))
                    cell("最低", "high").lparams(GridLayout.spec(0, GridLayout.CENTER), GridLayout.spec(1, GridLayout.LEFT, 1f))

                    space().lparams(GridLayout.spec(1, GridLayout.CENTER), GridLayout.spec(0, GridLayout.FILL, 1f)) { topMargin = dip(3); height = 0 }

                    cell("换手率", "open").lparams(GridLayout.spec(2, GridLayout.CENTER), GridLayout.spec(0, GridLayout.LEFT, 1f))
                    cell("市值", "high").lparams(GridLayout.spec(2, GridLayout.CENTER), GridLayout.spec(1, GridLayout.LEFT, 1f))
                    cell("成交量", "high").lparams(GridLayout.spec(2, GridLayout.CENTER), GridLayout.spec(1, GridLayout.LEFT, 1f)).apply {
                        visibility = View.GONE
                    }
                }.lparams(weight = 0.4f) { gravity = Gravity.BOTTOM; leftMargin = dip(30) }
            }.lparams(matchParent, wrapContent)

            relativeLayout {
                wait { until("market", converter = viewOfMarket) { inflate(it, this@verticalLayout) }  }
            }.lparams { topMargin = dip(12) }
        }
    }
}
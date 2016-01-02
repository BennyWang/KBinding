package com.benny.app.sample.viewcomponent.stock

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import com.benny.app.sample.model.MarketType
import com.benny.library.kbinding.converter.OneWayConverter
import com.benny.library.kbinding.dsl.*
import com.benny.library.kbinding.view.ViewBinderComponent
import org.jetbrains.anko.*
import rx.functions.Action1

/**
 * Created by benny on 12/31/15.
 */

class StockSubInfoHSUI : ViewBinderComponent<View> {
    override fun builder(): AnkoContext<View>.() -> Unit = {
        gridLayout {
            linearLayout {
                textView {
                    textSize = 12f
                    textColor = Color.parseColor("#999999")
                    text = "最高"
                }
                textView {
                    text = "--"
                    textSize = 12f
                    textColor = Color.WHITE
                    gravity = Gravity.RIGHT
                }.lparams(matchParent)
            }.lparams(GridLayout.spec(0, GridLayout.CENTER), GridLayout.spec(0, GridLayout.FILL, 1f)) { rightMargin = dip(5)}
            linearLayout {
                textView {
                    textSize = 12f
                    textColor = Color.parseColor("#999999")
                    text = "最低"
                }
                textView {
                    text = "--"
                    textSize = 12f
                    textColor = Color.WHITE
                    gravity = Gravity.RIGHT
                }.lparams(matchParent)
            }.lparams(GridLayout.spec(0, GridLayout.CENTER), GridLayout.spec(1, GridLayout.FILL, 1f)) { rightMargin = dip(5)}
            linearLayout {
                textView {
                    textSize = 12f
                    textColor = Color.parseColor("#999999")
                    text = "成交额"
                }
                textView {
                    text = "--"
                    textSize = 12f
                    textColor = Color.WHITE
                    gravity = Gravity.RIGHT
                }.lparams(matchParent)
            }.lparams(GridLayout.spec(0, GridLayout.CENTER), GridLayout.spec(2, GridLayout.FILL, 1f))

            linearLayout {
                textView {
                    textSize = 12f
                    textColor = Color.parseColor("#999999")
                    text = "市盈率"
                }
                textView {
                    text = "--"
                    textSize = 12f
                    textColor = Color.WHITE
                    gravity = Gravity.RIGHT
                }.lparams(matchParent)
            }.lparams(GridLayout.spec(1, GridLayout.CENTER), GridLayout.spec(0, GridLayout.FILL, 1f)) { rightMargin = dip(5); topMargin = dip(3) }
            linearLayout {
                textView {
                    textSize = 12f
                    textColor = Color.parseColor("#999999")
                    text = "市净率"
                }
                textView {
                    text = "--"
                    textSize = 12f
                    textColor = Color.WHITE
                    gravity = Gravity.RIGHT
                }.lparams(matchParent)
            }.lparams(GridLayout.spec(1, GridLayout.CENTER), GridLayout.spec(1, GridLayout.FILL, 1f)) { rightMargin = dip(5)}
            linearLayout {
                textView {
                    textSize = 12f
                    textColor = Color.parseColor("#999999")
                    text = "总市值"
                }
                textView {
                    text = "--"
                    textSize = 12f
                    textColor = Color.WHITE
                    gravity = Gravity.RIGHT
                }.lparams(matchParent)
            }.lparams(GridLayout.spec(1, GridLayout.CENTER), GridLayout.spec(2, GridLayout.FILL, 1f))

            linearLayout {
                textView {
                    textSize = 12f
                    textColor = Color.parseColor("#999999")
                    text = "振幅"
                }
                textView {
                    text = "--"
                    textSize = 12f
                    textColor = Color.WHITE
                    gravity = Gravity.RIGHT
                }.lparams(matchParent)
            }.lparams(GridLayout.spec(2, GridLayout.CENTER), GridLayout.spec(0, GridLayout.FILL, 1f)) { rightMargin = dip(5); topMargin = dip(3) }
            linearLayout {
                textView {
                    textSize = 12f
                    textColor = Color.parseColor("#999999")
                    text = "每股收益"
                }
                textView {
                    text = "--"
                    textSize = 12f
                    textColor = Color.WHITE
                    gravity = Gravity.RIGHT
                }.lparams(matchParent)
            }.lparams(GridLayout.spec(2, GridLayout.CENTER), GridLayout.spec(1, GridLayout.FILL, 1f)) { rightMargin = dip(5)}
            linearLayout {
                textView {
                    textSize = 12f
                    textColor = Color.parseColor("#999999")
                    text = "流通市值"
                }
                textView {
                    text = "--"
                    textSize = 12f
                    textColor = Color.WHITE
                    gravity = Gravity.RIGHT
                }.lparams(matchParent)
            }.lparams(GridLayout.spec(2, GridLayout.CENTER), GridLayout.spec(2, GridLayout.FILL, 1f))

        }
    }
}

class StockSubInfoHKUI : ViewBinderComponent<View> {
    override fun builder(): AnkoContext<View>.() -> Unit = {
        gridLayout {
            linearLayout {
                textView {
                    textSize = 12f
                    textColor = Color.parseColor("#999999")
                    text = "最高"
                }
                textView {
                    text = "--"
                    textSize = 12f
                    textColor = Color.WHITE
                    gravity = Gravity.RIGHT
                }.lparams(matchParent)
            }.lparams(GridLayout.spec(0, GridLayout.CENTER), GridLayout.spec(0, GridLayout.FILL, 1f)) { rightMargin = dip(5)}
            linearLayout {
                textView {
                    textSize = 12f
                    textColor = Color.parseColor("#999999")
                    text = "最低"
                }
                textView {
                    text = "--"
                    textSize = 12f
                    textColor = Color.WHITE
                    gravity = Gravity.RIGHT
                }.lparams(matchParent)
            }.lparams(GridLayout.spec(0, GridLayout.CENTER), GridLayout.spec(1, GridLayout.FILL, 1f)) { rightMargin = dip(5)}
            linearLayout {
                textView {
                    textSize = 12f
                    textColor = Color.parseColor("#999999")
                    text = "成交额"
                }
                textView {
                    text = "--"
                    textSize = 12f
                    textColor = Color.WHITE
                    gravity = Gravity.RIGHT
                }.lparams(matchParent)
            }.lparams(GridLayout.spec(0, GridLayout.CENTER), GridLayout.spec(2, GridLayout.FILL, 1f))

            linearLayout {
                textView {
                    textSize = 12f
                    textColor = Color.parseColor("#999999")
                    text = "52周高"
                }
                textView {
                    text = "--"
                    textSize = 12f
                    textColor = Color.WHITE
                    gravity = Gravity.RIGHT
                }.lparams(matchParent)
            }.lparams(GridLayout.spec(1, GridLayout.CENTER), GridLayout.spec(0, GridLayout.FILL, 1f)) { rightMargin = dip(5); topMargin = dip(3) }
            linearLayout {
                textView {
                    textSize = 12f
                    textColor = Color.parseColor("#999999")
                    text = "52周低"
                }
                textView {
                    text = "--"
                    textSize = 12f
                    textColor = Color.WHITE
                    gravity = Gravity.RIGHT
                }.lparams(matchParent)
            }.lparams(GridLayout.spec(1, GridLayout.CENTER), GridLayout.spec(1, GridLayout.FILL, 1f)) { rightMargin = dip(5)}
            linearLayout {
                textView {
                    textSize = 12f
                    textColor = Color.parseColor("#999999")
                    text = "股息"
                }
                textView {
                    text = "--"
                    textSize = 12f
                    textColor = Color.WHITE
                    gravity = Gravity.RIGHT
                }.lparams(matchParent)
            }.lparams(GridLayout.spec(1, GridLayout.CENTER), GridLayout.spec(2, GridLayout.FILL, 1f))
        }
    }
}


class StockInfoUI : ViewBinderComponent<View> {
    val visibility4HS = object : OneWayConverter<Boolean> {
        override fun convert(source: Any?): Boolean {
            return true
        }
    }

    val viewOfMarket = object : OneWayConverter<ViewBinderComponent<*>> {
        override fun convert(source: Any?): ViewBinderComponent<*> {
            if(source == null || source !is MarketType) return StockSubInfoHSUI()

            return if(source == MarketType.SH_SZ) StockSubInfoHSUI() else StockSubInfoHKUI()
        }
    }

    override fun builder(): AnkoContext<View>.() -> Unit = {
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
                        text = "10.28"
                    }
                    linearLayout {
                        val tvStyle = viewStyle<TextView> {
                            textSize = 20f
                            textColor = Color.WHITE
                            gravity = Gravity.CENTER_HORIZONTAL
                        }
                        textView {
                            style = tvStyle
                            text = "+1.30"
                        }.lparams(0, wrapContent, 1f)
                        textView {
                            style = tvStyle
                            text = "+5.80%"
                        }.lparams(0, wrapContent, 1f)
                    }
                }.lparams(weight = 0.6f)

                gridLayout {
                    verticalLayout {
                        textView {
                            textSize = 12f
                            textColor = Color.parseColor("#999999")
                            text = "今开"
                        }
                        textView {
                            //bind { text("open", mode = OneWay) }
                            textSize = 12f
                            textColor = Color.WHITE
                        }
                    }.lparams(GridLayout.spec(0, GridLayout.CENTER), GridLayout.spec(0, GridLayout.LEFT, 1f))
                    verticalLayout {
                        textView {
                            textSize = 12f
                            textColor = Color.parseColor("#999999")
                            text = "昨收"
                        }
                        textView {
                            //bind { text("previousClose", mode = OneWay) }
                            textSize = 12f
                            textColor = Color.WHITE
                        }
                    }.lparams(GridLayout.spec(0, GridLayout.CENTER), GridLayout.spec(1, GridLayout.LEFT, 1f))
                    verticalLayout {
                        textView {
                            textSize = 12f
                            textColor = Color.parseColor("#999999")
                            text = "换手率"
                        }
                        textView {
                            //bind { text("turnOverRate", mode = OneWay) }
                            textSize = 12f
                            textColor = Color.WHITE
                        }
                    }.lparams(GridLayout.spec(1, GridLayout.CENTER), GridLayout.spec(0, GridLayout.LEFT, 1f)) { topMargin = dip(3) }
                    verticalLayout {
                        visibility = View.GONE
                        textView {
                            textSize = 12f
                            textColor = Color.parseColor("#999999")
                            text = "市值"
                        }
                        textView {
                            //bind { text("sz", mode = OneWay) }
                            textSize = 12f
                            textColor = Color.WHITE
                        }
                    }.lparams(GridLayout.spec(1, GridLayout.CENTER), GridLayout.spec(0, GridLayout.LEFT, 1f))
                    verticalLayout {
                        textView {
                            textSize = 12f
                            textColor = Color.parseColor("#999999")
                            text = "成交量"
                        }
                        textView {
                            //bind { text("cjl", mode = OneWay) }
                            textSize = 12f
                            textColor = Color.WHITE
                            text = "--"
                        }
                    }.lparams(GridLayout.spec(1, GridLayout.CENTER), GridLayout.spec(1, GridLayout.LEFT, 1f))
                }.lparams(weight = 0.4f) { gravity = Gravity.BOTTOM; leftMargin = dip(30) }
            }.lparams(matchParent, wrapContent)

            relativeLayout {
                wait { until("market", converter = viewOfMarket) { inflate(it, this@verticalLayout) }  }
            }.lparams { topMargin = dip(12) }
        }
    }
}
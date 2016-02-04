package com.benny.app.sample.ui.layout.item

import android.graphics.Typeface
import android.text.TextUtils
import android.view.Gravity
import com.benny.app.sample.R
import com.benny.app.sample.ui.extension.simpleDraweeView
import com.benny.library.kbinding.common.bindings.src
import com.benny.library.kbinding.common.bindings.text
import com.benny.library.kbinding.common.bindings.textWeight
import com.benny.library.kbinding.converter.StringConverter
import com.benny.library.kbinding.dsl.OneWay
import com.benny.library.kbinding.dsl.bind
import com.benny.library.kbinding.dsl.resolveAttribute
import com.benny.library.kbinding.view.ItemViewBinderComponent
import org.jetbrains.anko.*

/**
 * Created by benny on 1/30/16.
 */

class MovieItemView : ItemViewBinderComponent {
    override fun builder(): AnkoContext<*>.() -> Unit = {
        relativeLayout {
            backgroundResource = resolveAttribute(android.R.attr.selectableItemBackground)
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
                        textView { textResource = R.string.score_label }
                        textView {
                            bind { text("score", mode = OneWay, converter = StringConverter()) }
                        }
                    }.lparams { bottomMargin = dip(8) }
                    linearLayout {
                        textView { textResource = R.string.cast_label }
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
package com.benny.app.sample.ui.activity

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Gravity
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import com.benny.library.kbinding.converter.StringConverter
import com.benny.library.kbinding.dsl.*
import com.benny.library.kbinding.view.ViewBinderComponent
import com.benny.library.kbinding.view.setContentView
import com.benny.library.kbinding.common.bindings.*

import com.benny.app.sample.ui.extension.progressBar
import com.benny.app.sample.network.service.douban.DoubanService
import com.benny.app.sample.ui.extension.simpleDraweeView
import com.benny.app.sample.ui.layout.TitleToolBarView
import com.benny.app.sample.viewmodel.MovieViewModel

/**
 * Created by benny on 12/30/15.
 */

class MovieDetailsActivity : BaseActivity() {
    lateinit var toolBar: Toolbar
    val movieViewModel: MovieViewModel = MovieViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MovieDetailsActivityUI().setContentView(this).bindTo(movieViewModel)

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = ""

        DoubanService.getInstance().movie(intent.getStringExtra("id")).subscribe { movieViewModel.updateData(it) }
    }


    class MovieDetailsActivityUI : ViewBinderComponent<MovieDetailsActivity> {
        override fun builder(): AnkoContext<*>.() -> Unit = {
            verticalLayout {
                appBarLayout {
                    (owner as MovieDetailsActivity).toolBar = inflate(TitleToolBarView("影片详情"), this@appBarLayout) as Toolbar
                }
                frameLayout {
                    scrollView {
                        backgroundColor = Color.WHITE
                        verticalLayout {
                            frameLayout {
                                verticalPadding = dip(14)
                                backgroundColor = Color.parseColor("#9a7972")
                                simpleDraweeView {
                                    bind { src("bigCover", mode = OneWay) }
                                }.lparams(dip(150), dip(214)) { gravity = Gravity.CENTER }
                            }.lparams(matchParent, wrapContent) { bottomMargin = dip(14) }

                            textView {
                                textSize = 16f
                                textWeight = Typeface.BOLD
                                bind { text("title", mode = OneWay) }
                            }.lparams { bottomMargin = dip(8); leftMargin = dip(14) }

                            linearLayout {
                                textView {
                                    bind { text("score", mode = OneWay, converter = StringConverter()) }
                                }
                                textView { text = "分" }.lparams { rightMargin = dip(14) }

                                textView {
                                    bind { text("ratingsCount", mode = OneWay, converter = StringConverter()) }
                                }
                                textView { text = "人评分" }
                            }.lparams { bottomMargin = dip(8); leftMargin = dip(14) }

                            textView {
                                singleLine = true
                                ellipsize = TextUtils.TruncateAt.END
                                bind { text("casts", mode = OneWay) }
                            }.lparams { bottomMargin = dip(8); leftMargin = dip(14) }

                            textView {
                                singleLine = true
                                ellipsize = TextUtils.TruncateAt.END
                                bind { text("genres", mode = OneWay) }
                            }.lparams { bottomMargin = dip(8); leftMargin = dip(14) }

                            view {
                                backgroundColor = Color.BLACK
                            }.lparams(matchParent, 1) { bottomMargin = dip(8); leftMargin = dip(14) }

                            textView { text = "剧情简介" }.lparams { bottomMargin = dip(8); leftMargin = dip(14) }

                            textView {
                                bind { text("summary", mode = OneWay, converter = StringConverter()) }
                            }.lparams { bottomMargin = dip(8); leftMargin = dip(14); rightMargin = dip(14) }
                        }
                    }
                    frameLayout {
                        backgroundColor = Color.WHITE
                        progressBar(android.R.attr.progressBarStyleSmall).lparams { gravity = Gravity.CENTER }
                        wait { until("movie") { fadeOut() } }
                    }
                }.lparams(matchParent, matchParent)
            }

        }
    }
}
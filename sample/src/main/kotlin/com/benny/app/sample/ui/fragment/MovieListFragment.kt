package com.benny.app.sample.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benny.app.sample.network.service.douban.DoubanService
import com.benny.app.sample.network.service.douban.model.Movie
import com.benny.app.sample.ui.activity.MovieDetailsActivity
import com.benny.app.sample.ui.extension.progressBar
import com.benny.app.sample.ui.layout.item.LoadingItemView
import com.benny.app.sample.ui.layout.item.MovieItemView
import com.benny.app.sample.utils.generateViewId
import com.benny.app.sample.viewmodel.MovieViewModel
import com.benny.library.kbinding.annotation.Command
import com.benny.library.kbinding.annotation.Property
import com.benny.library.kbinding.common.bindings.*
import com.benny.library.kbinding.dsl.bind
import com.benny.library.kbinding.dsl.wait
import com.benny.library.kbinding.support.v4.bindings.refresh
import com.benny.library.kbinding.adapterview.bindings.adapter
import com.benny.library.kbinding.adapterview.bindings.itemClick
import com.benny.library.kbinding.adapterview.bindings.paging
import com.benny.library.kbinding.adapterview.converter.ListToRecyclerPagingAdapterConverter
import com.benny.library.kbinding.adapterview.viewcreator.pagingViewCreator
import com.benny.library.kbinding.view.ViewBinderComponent
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*
import kotlin.properties.Delegates

/**
 * Created by benny on 1/28/16.
 */

class MovieListFragment : BaseFragment() {
    var contentView: View? = null

    @delegate:Property
    var movies: List<Movie>? by Delegates.property()

    @Command
    fun movieDetail(params: Int) {
        startActivity<MovieDetailsActivity>("id" to movies!![params].id)
    }

    @Command
    fun reloadMovies(canExecute: (Boolean) -> Unit) {
        toast("reload movie finished")
        canExecute(true)
    }

    @Command
    fun loadMoreMovies(canExecute: (Boolean) -> Unit) {
        contentView?.postDelayed( { canExecute(true) } , 2000)
    }

    fun fetchMovies() {
        DoubanService.instance.moviesInTheaters().map { it -> it.subjects.toList()}.onErrorReturn { listOf() }.subscribe { movies = it }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(contentView == null) {
            contentView = MovieListFragmentUI().createViewBinder(act, this).bindTo(this)
            fetchMovies()
        }
        return contentView
    }

    class MovieListFragmentUI() : ViewBinderComponent<MovieListFragment> {
        override fun builder(): AnkoContext<*>.() -> Unit = {
            relativeLayout() {
                backgroundColor = Color.WHITE
                swipeRefreshLayout {
                    bind { refresh("reloadMovies") }
                    recyclerView {
                        id = generateViewId()
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        addItemDecoration(HorizontalDividerItemDecoration.Builder(ctx).color(Color.parseColor("#f2f2f2")).margin(dip(14), 0).size(1).build())
                        bind { paging("loadMoreMovies") }
                        bind { adapter("movies", converter = ListToRecyclerPagingAdapterConverter((owner as MovieListFragment).pagingViewCreator(LoadingItemView(), MovieItemView(), ::MovieViewModel))) }
                        bind { itemClick("movieDetail") }
                    }.lparams(matchParent, matchParent)
                }
                frameLayout {
                    backgroundColor = Color.WHITE
                    progressBar(android.R.attr.progressBarStyleSmall).lparams { gravity = Gravity.CENTER }
                    wait { until("movies") { fadeOut() } }
                }.lparams(matchParent, matchParent)
            }
        }
    }
}
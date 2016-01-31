package com.benny.app.sample.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.jetbrains.anko.*

import com.benny.library.kbinding.view.ViewBinderComponent
import com.benny.library.kbinding.bind.BindingDelegate
import com.benny.library.kbinding.bind.Command
import com.benny.library.kbinding.dsl.*
import com.benny.library.kbinding.common.converter.ListToPagingAdapterConverter
import com.benny.library.kbinding.support.v4.bindings.refresh

import com.benny.app.sample.network.service.douban.DoubanService
import com.benny.app.sample.network.service.douban.model.Movie
import com.benny.app.sample.ui.activity.MovieDetailsActivity
import com.benny.app.sample.ui.layout.item.MovieItemView
import com.benny.app.sample.ui.extension.progressBar
import com.benny.app.sample.ui.layout.item.LoadingItemView
import com.benny.app.sample.viewmodel.MovieViewModel
import com.benny.library.kbinding.common.bindings.*
import org.jetbrains.anko.support.v4.*

/**
 * Created by benny on 1/28/16.
 */

class MovieListFragment : BaseFragment() {
    var contentView: View? = null
    val bindingDelegate = BindingDelegate()

    var movies: List<Movie>? by bindingDelegate.bindProperty("movies")
    val movieDetail: Command<Int> by bindingDelegate.bindCommand("movieDetail") { params, canExecute ->
        startActivity<MovieDetailsActivity>("id" to movies!![params].id)
    }

    val reloadMovies: Command<Unit> by bindingDelegate.bindCommand("reloadMovies") { params, canExecute ->
        toast("reload movie finished")
        canExecute(true)
    }

    val loadMoreMovies: Command<Pair<Int, Any?> > by bindingDelegate.bindCommand("loadMoreMovies") { params, canExecute ->
        longToast("load more movie position: " + params.first + "   data: " + params.second)
        contentView?.postDelayed( { canExecute(true) } , 2000)
    }

    fun fetchMovies() {
        DoubanService.getInstance().moviesInTheaters().map { it -> it.subjects.toList()}.onErrorReturn { listOf() }.subscribe { movies = it }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(contentView == null) {
            contentView = MovieListFragmentUI().createViewBinder(act, this).bindTo(bindingDelegate)
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
                    listView {
                        dividerHeight = 1
                        bind { paging("loadMoreMovies") }
                        bind { adapter("movies", converter = ListToPagingAdapterConverter((owner as MovieListFragment).pagingViewCreator(LoadingItemView(), MovieItemView(), ::MovieViewModel))) }
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
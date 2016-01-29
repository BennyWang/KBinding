package com.benny.app.sample.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benny.app.sample.extension.progressBar
import com.benny.app.sample.network.service.douban.DoubanService
import com.benny.app.sample.network.service.douban.model.Movie
import com.benny.app.sample.ui.activity.MovieDetailsActivity
import com.benny.app.sample.ui.activity.StockDetailsActivity
import com.benny.app.sample.viewcomponent.MovieItemView
import com.benny.library.kbinding.converter.ListToAdapterConverter
import com.benny.library.kbinding.view.ViewBinderComponent
import com.benny.app.sample.viewmodel.MovieViewModel
import com.benny.library.kbinding.bind.BindingDelegate
import com.benny.library.kbinding.bind.Command
import com.benny.library.kbinding.dsl.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity

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
               listView {
                   dividerHeight = 1
                   bind { adapter("movies", converter = ListToAdapterConverter((owner as MovieListFragment).viewCreator(MovieItemView(), ::MovieViewModel))) }
                   bind { itemClick("movieDetail") }
               }.lparams(matchParent, matchParent)
               frameLayout {
                   backgroundColor = Color.WHITE
                   progressBar(android.R.attr.progressBarStyleSmall).lparams { gravity = Gravity.CENTER }
                   wait { until("movies") { fadeOut() } }
               }.lparams(matchParent, matchParent)
           }
        }
    }
}
package com.benny.app.sample.viewmodel

import com.benny.app.sample.network.service.douban.model.Movie
import com.benny.library.kbinding.bind.ItemViewModel

/**
 * Created by benny on 11/19/15.
 */

class MovieViewModel() : ItemViewModel<Movie>() {
    public var movie: Movie? by bindProperty("movie")

    public val title: String? by bindProperty("title", "movie") { movie!!.title }
    public val smallCover: String? by bindProperty("smallCover", "movie") { movie!!.images.small }
    public val bigCover: String? by bindProperty("bigCover", "movie") { movie!!.images.large }
    public val score: Float by bindProperty("score", "movie") { movie!!.rating.average }
    public val casts: String? by bindProperty("casts", "movie") { movie!!.casts.map { it -> it.name }.joinToString("/") }
    public val genres: String? by bindProperty("genres", "movie") { movie!!.genres.joinToString("/") }
    public val summary: String? by bindProperty("summary", "movie") { movie!!.summary }
    public val ratingsCount: Int by bindProperty("ratingsCount", "movie") { movie!!.ratingsCount }

    override fun updateData(t: Movie?) {
        movie = t
    }
}
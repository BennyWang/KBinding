package com.benny.library.kbinding.util

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import rx.subjects.PublishSubject

/**
 * Created by ldy on 16/8/25.
 * Cache subject for activity,invoke remove when activity onDestroy,otherwise activity memory leak
 */
object SubjectCache {
    private val cache: MutableMap<Context, MutableMap<View, PublishSubject<*>>> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    fun <T> getOrCreatePublishSubject(view: View): PublishSubject<T> {
        return cache.getOrPut(view.context!!, {
            mutableMapOf<View, PublishSubject<*>>()
        }).getOrPut(view, {
            PublishSubject.create<T>()
        }) as PublishSubject<T>
    }

    fun removeContext(context: Context){
        cache.remove(context)
    }

}

fun <T> View.getSubject(): PublishSubject<T> = SubjectCache.getOrCreatePublishSubject<T>(this)
fun View.getUnitSubject(): PublishSubject<Unit> = getSubject()

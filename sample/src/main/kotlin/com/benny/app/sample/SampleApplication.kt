package com.benny.app.sample

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

/**
 * Created by benny on 12/23/15.
 */
class SampleApplication : Application() {
    lateinit var refWatcher: RefWatcher

    override fun onCreate() {
        super.onCreate()

        ApplicationContext.init(this)
        refWatcher = LeakCanary.install(this)
    }

    public companion object {
        public fun getRefWatcher(context: Context) : RefWatcher {
            val application =  context.applicationContext as SampleApplication;
            return application.refWatcher;
        }
    }
}
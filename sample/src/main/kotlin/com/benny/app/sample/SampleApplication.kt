package com.benny.app.sample

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by benny on 12/23/15.
 */
class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ApplicationContext.init(this)
        Fresco.initialize(this);
    }
}
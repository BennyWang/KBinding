package com.benny.library.neobinding.kotlin.view

import android.app.Activity
import android.view.View

/**
 * Created by benny on 12/10/15.
 */
object  ViewFinderFactory {

    public fun create(activity: Activity): ViewFinder {
        return ActivityFinder(activity)
    }

    public fun create(view: View): ViewFinder {
        return DefaultFinder(view)
    }

    class ActivityFinder(val activity: Activity): ViewFinder {

        override fun <T: View> find(viewId: Int): T {
            return activity.findViewById(viewId) as T
        }
    }

    class DefaultFinder(val view: View): ViewFinder {

        override fun <T: View> find(viewId: Int): T {
            return view.findViewById(viewId) as T
        }
    }
}


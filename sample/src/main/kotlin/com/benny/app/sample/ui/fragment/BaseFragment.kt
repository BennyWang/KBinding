package com.benny.app.sample.ui.fragment

import android.os.Bundle
import android.util.Log
import com.benny.app.sample.event.BaseEvent
import com.trello.rxlifecycle.components.support.RxFragment
import de.greenrobot.event.EventBus

/**
 * Created by benny on 12/21/15.
 */

open class BaseFragment : RxFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    public fun onEvent(event: BaseEvent) {
        Log.d("EventBus", "base event")
    }
}
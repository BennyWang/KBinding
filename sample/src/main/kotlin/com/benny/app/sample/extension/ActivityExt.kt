package com.benny.app.sample.extension

import android.content.Context
import com.benny.library.neobinding.bind.BindingContext
import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.FragmentEvent
import com.trello.rxlifecycle.RxLifecycle
import com.trello.rxlifecycle.components.ActivityLifecycleProvider
import com.trello.rxlifecycle.components.FragmentLifecycleProvider
import com.trello.rxlifecycle.components.support.RxFragment
import com.trello.rxlifecycle.components.support.RxFragmentActivity
import rx.Observable

/**
 * Created by benny on 12/16/15.
 */

public fun ActivityLifecycleProvider.bindingContext(context: Context): BindingContext  {
    return object : BindingContext {
        override val context: Context get() = context

        override fun <T> applyLifecycle(): Observable.Transformer<T, T> {
            return RxLifecycle.bindUntilActivityEvent(lifecycle(), ActivityEvent.DESTROY)
        }

    }
}

public fun FragmentLifecycleProvider.bindingContext(context: Context): BindingContext {
    return object : BindingContext {
        override val context: Context get() = context

        override fun <T> applyLifecycle(): Observable.Transformer<T, T> {
            return RxLifecycle.bindUntilFragmentEvent(lifecycle(), FragmentEvent.DESTROY_VIEW)
        }

    }
}
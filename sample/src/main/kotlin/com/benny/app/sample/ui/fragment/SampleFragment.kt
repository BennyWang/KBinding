package com.benny.app.sample.ui.fragment

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benny.app.sample.event.BaseEvent
import com.benny.app.sample.event.TestEvent
import com.benny.app.sample.extension.bindingContext
import com.benny.library.neobinding.bind.BindingContext
import com.benny.library.neobinding.view.ViewBinderComponent
import de.greenrobot.event.EventBus
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.act

/**
 * Created by benny on 12/21/15.
 */
class SampleFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return UI { SampleFragmentUI(bindingContext(act)).createView(this) }.view
    }

    public fun onEvent(event: TestEvent) {
        Log.d("EventBus", "test event")
    }

    class SampleFragmentUI(val bindingContext: BindingContext) : ViewBinderComponent<Fragment> {
        override fun builder(): AnkoContext<*>.() -> Unit = {
            verticalLayout {
                button {
                    text = "base event"
                    onClick { EventBus.getDefault().post(BaseEvent()) }
                }

                button {
                    text = "fragment event"
                    onClick { EventBus.getDefault().post(TestEvent()) }
                }
            }
        }
    }
}
package com.benny.app.sample.ui.fragment

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benny.library.kbinding.view.ViewBinderComponent
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

/**
 * Created by benny on 12/21/15.
 */

class SampleFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return UI { SampleFragmentUI().createView(this) }.view
    }

    class SampleFragmentUI() : ViewBinderComponent<Fragment> {
        override fun builder(): AnkoContext<*>.() -> Unit = {
            verticalLayout {
            }
        }
    }
}
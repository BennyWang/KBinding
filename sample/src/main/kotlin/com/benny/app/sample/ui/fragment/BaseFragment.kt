package com.benny.app.sample.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.benny.app.sample.event.BaseEvent
import com.benny.library.neobinding.bind.BindingDisposer
import com.benny.library.neobinding.view.BindingDisposerGenerator

import de.greenrobot.event.EventBus

/**
 * Created by benny on 12/21/15.
 */

open class BaseFragment : Fragment(), BindingDisposerGenerator {
    override val bindingDisposer: BindingDisposer = BindingDisposer()

    override fun onDestroy() {
        super.onDestroy()
        bindingDisposer.unbind()
    }

}
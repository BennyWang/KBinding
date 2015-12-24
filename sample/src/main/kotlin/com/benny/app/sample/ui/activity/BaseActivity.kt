package com.benny.app.sample.ui.activity

import android.support.v4.app.FragmentActivity
import com.benny.library.kbinding.bind.BindingDisposer
import com.benny.library.kbinding.view.BindingDisposerGenerator

/**
 * Created by benny on 12/23/15.
 */
open class BaseActivity : FragmentActivity(), BindingDisposerGenerator {
    override val bindingDisposer: BindingDisposer = BindingDisposer()

    override fun onDestroy() {
        super.onDestroy()
        bindingDisposer.unbind()
    }
}
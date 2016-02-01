package com.benny.app.sample.ui.fragment

import android.support.v4.app.Fragment
import com.benny.library.kbinding.bind.BindingDelegate
import com.benny.library.kbinding.bind.BindingDisposer
import com.benny.library.kbinding.bind.ViewModel
import com.benny.library.kbinding.view.BindingDisposerGenerator


/**
 * Created by benny on 12/21/15.
 */

open class BaseFragment : Fragment(), BindingDisposerGenerator, BindingDelegate {
    override val viewModel: ViewModel = ViewModel()
    override val bindingDisposer: BindingDisposer = BindingDisposer()

    override fun onDestroy() {
        super.onDestroy()
        bindingDisposer.unbind()
    }

}
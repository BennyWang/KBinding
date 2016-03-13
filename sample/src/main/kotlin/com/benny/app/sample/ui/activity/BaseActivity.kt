package com.benny.app.sample.ui.activity

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.benny.library.kbinding.bind.BindingDelegate
import com.benny.library.kbinding.bind.BindingDisposer
import com.benny.library.kbinding.view.BindingDisposerGenerator
import com.benny.library.kbinding.viewmodel.ViewModel

/**
 * Created by benny on 12/23/15.
 */

open class BaseActivity : AppCompatActivity(), BindingDisposerGenerator, BindingDelegate {
    override val viewModel: ViewModel = ViewModel()
    override val bindingDisposer: BindingDisposer = BindingDisposer()

    override fun onDestroy() {
        super.onDestroy()
        bindingDisposer.unbind()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        android.R.id.home -> { onBackPressed(); true }
        else -> super.onOptionsItemSelected(item)
    }
}